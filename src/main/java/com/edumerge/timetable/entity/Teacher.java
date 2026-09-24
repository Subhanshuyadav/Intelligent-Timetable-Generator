package com.edumerge.timetable.entity;

import jakarta.persistence.*;

@Entity @Table(name="teachers", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class Teacher {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    public Teacher() {} public Teacher(String name){this.name=name;}
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
}
