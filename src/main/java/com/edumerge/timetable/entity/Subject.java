package com.edumerge.timetable.entity;

import jakarta.persistence.*;

@Entity @Table(name="subjects", uniqueConstraints=@UniqueConstraint(columnNames="code"))
public class Subject {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String code;
    @Column(nullable=false) private String name;
    private int sessionsPerWeek;
    @Column(nullable=false) private boolean lab;
    public Subject() {}
    public Subject(String code,String name,int sessionsPerWeek,boolean lab){this.code=code;this.name=name;this.sessionsPerWeek=sessionsPerWeek;this.lab=lab;}
    public Long getId(){return id;} public String getCode(){return code;} public void setCode(String v){code=v;}
    public String getName(){return name;} public void setName(String v){name=v;} public int getSessionsPerWeek(){return sessionsPerWeek;} public void setSessionsPerWeek(int v){sessionsPerWeek=v;}
    public boolean isLab(){return lab;} public void setLab(boolean v){lab=v;}
}
