package com.edumerge.timetable.service;

import com.edumerge.timetable.dto.*;
import com.edumerge.timetable.entity.*;
import com.edumerge.timetable.exception.ApiException;
import com.edumerge.timetable.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class TimetableService {
    private final DivisionRepository divisions; private final TeacherRepository teachers; private final RoomRepository rooms;
    private final SubjectRepository subjects; private final CourseAllocationRepository allocations; private final TimetableEntryRepository entries;
    public TimetableService(DivisionRepository d,TeacherRepository t,RoomRepository r,SubjectRepository s,CourseAllocationRepository a,TimetableEntryRepository e){
        divisions=d;teachers=t;rooms=r;subjects=s;allocations=a;entries=e;
    }
    public List<Division> divisions(){return divisions.findAll();} public List<Teacher> teachers(){return teachers.findAll();}
    public List<Room> rooms(){return rooms.findAll();} public List<Subject> subjects(){return subjects.findAll();}
    public List<CourseAllocation> allocations(){return allocations.findAll();}
    public List<TimetableResponse> timetable(){return entries.findAll().stream().map(this::map).toList();}
    private TimetableResponse map(TimetableEntry e){return new TimetableResponse(e.getId(),e.getDivision().getName(),e.getSubject().getCode()+" - "+e.getSubject().getName(),e.getTeacher().getName(),e.getRoom().getName(),e.getDayOfWeek(),e.getPeriodNumber());}

    public Division addDivision(String name,int count){return divisions.save(new Division(name,count));}
    public Teacher addTeacher(String name){
        if(teachers.existsByNameIgnoreCase(name.trim())){
            throw new ApiException("Teacher already exists: " + name);
        }
        return teachers.save(new Teacher(name.trim()));
    }
    public Room addRoom(String name,int capacity,boolean lab){return rooms.save(new Room(name,capacity,lab));}
    public Subject addSubject(String code,String name,int sessions,boolean lab){
        if(sessions<1||sessions>20) throw new ApiException("sessionsPerWeek must be between 1 and 20");
        return subjects.save(new Subject(code,name,sessions,lab));
    }
    public CourseAllocation addAllocation(AllocationRequest r){
        Division d=divisions.findById(r.divisionId()).orElseThrow(()->new ApiException("Division not found"));
        Subject s=subjects.findById(r.subjectId()).orElseThrow(()->new ApiException("Subject not found"));
        Teacher t=teachers.findById(r.teacherId()).orElseThrow(()->new ApiException("Teacher not found"));
        return allocations.save(new CourseAllocation(d,s,t));
    }

    @Transactional
    public List<TimetableResponse> generate(GenerateRequest req){
        if(allocations.count()==0) throw new ApiException("Add at least one course allocation first");
        if(rooms.count()==0) throw new ApiException("Add at least one classroom first");
        entries.deleteAllInBatch();
        List<Slot> slots=new ArrayList<>();
        for(int d=1;d<=req.days();d++) for(int p=1;p<=req.periodsPerDay();p++) slots.add(new Slot(d,p));
        List<Task> tasks=new ArrayList<>();
        for(CourseAllocation a:allocations.findAll()){
            for(int i=0;i<a.getSubject().getSessionsPerWeek();i++) tasks.add(new Task(a));
        }
        // Most constrained first: lab classes and larger divisions first.
        tasks.sort(Comparator.comparing((Task x)->!x.a().getSubject().isLab()).thenComparing(x->-x.a().getDivision().getStudentCount()));
        Map<Key,TimetableEntry> placed=new HashMap<>();
        if(!backtrack(tasks,0,slots,placed)){
            throw new ApiException("No feasible timetable exists for the current constraints. Try adding rooms/periods or reducing weekly sessions.");
        }
        entries.saveAll(placed.values());
        return timetable();
    }
    private boolean backtrack(List<Task> tasks,int index,List<Slot> slots,Map<Key,TimetableEntry> placed){
        if(index==tasks.size()) return true;
        Task task=tasks.get(index); CourseAllocation a=task.a();
        List<Slot> shuffled=new ArrayList<>(slots); Collections.shuffle(shuffled,new Random(42L+index));
        for(Slot slot:shuffled){
            if(hasDivisionConflict(placed,a.getDivision(),slot) || hasTeacherConflict(placed,a.getTeacher(),slot)) continue;
            for(Room room:rooms.findAll()){
                if(room.getCapacity()<a.getDivision().getStudentCount()) continue;
                if(a.getSubject().isLab() && !room.isLab()) continue;
                if(hasRoomConflict(placed,room,slot)) continue;
                Key key=new Key(a.getDivision().getId(),slot.day(),slot.period(),a.getSubject().getId(),index);
                placed.put(key,new TimetableEntry(a.getDivision(),a.getSubject(),a.getTeacher(),room,slot.day(),slot.period()));
                if(backtrack(tasks,index+1,slots,placed)) return true;
                placed.remove(key);
            }
        }
        return false;
    }
    private boolean hasDivisionConflict(Map<Key,TimetableEntry> m,Division d,Slot s){return m.values().stream().anyMatch(e->e.getDivision().getId().equals(d.getId())&&same(e,s));}
    private boolean hasTeacherConflict(Map<Key,TimetableEntry> m,Teacher t,Slot s){return m.values().stream().anyMatch(e->e.getTeacher().getId().equals(t.getId())&&same(e,s));}
    private boolean hasRoomConflict(Map<Key,TimetableEntry> m,Room r,Slot s){return m.values().stream().anyMatch(e->e.getRoom().getId().equals(r.getId())&&same(e,s));}
    private boolean same(TimetableEntry e,Slot s){return e.getDayOfWeek()==s.day()&&e.getPeriodNumber()==s.period();}
    private record Slot(int day,int period){} private record Task(CourseAllocation a){}
    private record Key(Long d,int day,int period,Long subject,int occurrence){}
}
