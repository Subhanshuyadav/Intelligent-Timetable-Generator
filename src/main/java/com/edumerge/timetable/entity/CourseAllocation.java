package com.edumerge.timetable.entity;

import jakarta.persistence.*;

@Entity @Table(name="course_allocations")
public class CourseAllocation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Division division;
    @ManyToOne(optional=false) private Subject subject;
    @ManyToOne(optional=false) private Teacher teacher;
    public CourseAllocation() {}
    public CourseAllocation(Division d,Subject s,Teacher t){division=d;subject=s;teacher=t;}
    public Long getId(){return id;} public Division getDivision(){return division;} public void setDivision(Division v){division=v;}
    public Subject getSubject(){return subject;} public void setSubject(Subject v){subject=v;} public Teacher getTeacher(){return teacher;} public void setTeacher(Teacher v){teacher=v;}
}
