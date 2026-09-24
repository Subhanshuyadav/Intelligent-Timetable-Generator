package com.edumerge.timetable.config;

import com.edumerge.timetable.dto.AllocationRequest;
import com.edumerge.timetable.entity.*;
import com.edumerge.timetable.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoData {
 @Bean CommandLineRunner seed(DivisionRepository d,TeacherRepository t,RoomRepository r,SubjectRepository s,CourseAllocationRepository a){
  return args -> {
   if(d.count()>0) return;
   Division cseA=d.save(new Division("CSE-A",55)); Division cseB=d.save(new Division("CSE-B",48));
   Teacher anil=t.save(new Teacher("Dr. Anil Kumar")); Teacher priya=t.save(new Teacher("Dr. Priya Sharma")); Teacher rahul=t.save(new Teacher("Prof. Rahul Singh")); Teacher neha=t.save(new Teacher("Prof. Neha Verma"));
   r.save(new Room("R-101",60,false)); r.save(new Room("R-102",60,false)); r.save(new Room("LAB-1",60,true));
   Subject java=s.save(new Subject("CS301","Advanced Java",4,false)); Subject db=s.save(new Subject("CS302","Database Systems",4,false)); Subject ai=s.save(new Subject("CS303","AI Fundamentals",3,false)); Subject lab=s.save(new Subject("CS304L","Java Lab",2,true));
   a.save(new CourseAllocation(cseA,java,anil)); a.save(new CourseAllocation(cseA,db,priya)); a.save(new CourseAllocation(cseA,ai,rahul)); a.save(new CourseAllocation(cseA,lab,anil));
   a.save(new CourseAllocation(cseB,java,anil)); a.save(new CourseAllocation(cseB,db,neha)); a.save(new CourseAllocation(cseB,ai,rahul)); a.save(new CourseAllocation(cseB,lab,anil));
  };
 }
}
