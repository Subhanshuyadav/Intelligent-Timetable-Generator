package com.edumerge.timetable.controller;

import com.edumerge.timetable.dto.*;
import com.edumerge.timetable.entity.*;
import com.edumerge.timetable.service.TimetableService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api")
public class TimetableController {
    private final TimetableService service;
    public TimetableController(TimetableService service){this.service=service;}

    @GetMapping("/divisions") public List<Division> divisions(){return service.divisions();}
    @GetMapping("/teachers") public List<Teacher> teachers(){return service.teachers();}
    @GetMapping("/rooms") public List<Room> rooms(){return service.rooms(); }
    @GetMapping("/subjects") public List<Subject> subjects(){return service.subjects();}
    @GetMapping("/allocations") public List<CourseAllocation> allocations(){return service.allocations();}
    @GetMapping("/timetable") public List<TimetableResponse> timetable(){return service.timetable();}

    public record DivisionRequest(@NotBlank String name,@Min(1) int studentCount){}
    public record TeacherRequest(@NotBlank String name){}
    public record RoomRequest(@NotBlank String name,@Min(1) int capacity,boolean lab){}
    public record SubjectRequest(@NotBlank String code,@NotBlank String name,@Min(1) @Max(20) int sessionsPerWeek,boolean lab){}

    @PostMapping("/divisions") public Division addDivision(@Valid @RequestBody DivisionRequest r){return service.addDivision(r.name(),r.studentCount());}
    @PostMapping("/teachers") public Teacher addTeacher(@Valid @RequestBody TeacherRequest r){return service.addTeacher(r.name());}
    @PostMapping("/rooms") public Room addRoom(@Valid @RequestBody RoomRequest r){return service.addRoom(r.name(),r.capacity(),r.lab());}
    @PostMapping("/subjects") public Subject addSubject(@Valid @RequestBody SubjectRequest r){return service.addSubject(r.code(),r.name(),r.sessionsPerWeek(),r.lab());}
    @PostMapping("/allocations") public CourseAllocation addAllocation(@Valid @RequestBody AllocationRequest r){return service.addAllocation(r);}
    @PostMapping("/generate") public List<TimetableResponse> generate(@Valid @RequestBody GenerateRequest r){return service.generate(r);}
}
