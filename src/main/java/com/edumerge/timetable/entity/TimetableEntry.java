package com.edumerge.timetable.entity;

import jakarta.persistence.*;

@Entity @Table(name="timetable_entries", indexes={@Index(columnList="dayOfWeek,periodNumber")})
public class TimetableEntry {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Division division;
    @ManyToOne(optional=false) private Subject subject;
    @ManyToOne(optional=false) private Teacher teacher;
    @ManyToOne(optional=false) private Room room;
    @Column(nullable=false) private int dayOfWeek;
    @Column(nullable=false) private int periodNumber;
    public TimetableEntry() {}
    public TimetableEntry(Division d,Subject s,Teacher t,Room r,int day,int period){division=d;subject=s;teacher=t;room=r;dayOfWeek=day;periodNumber=period;}
    public Long getId(){return id;} public Division getDivision(){return division;} public Subject getSubject(){return subject;} public Teacher getTeacher(){return teacher;} public Room getRoom(){return room;}
    public int getDayOfWeek(){return dayOfWeek;} public int getPeriodNumber(){return periodNumber;}
}
