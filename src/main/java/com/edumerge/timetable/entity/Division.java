package com.edumerge.timetable.entity;

import jakarta.persistence.*;

@Entity
@Table(name="divisions", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Division {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String name;
    private int studentCount;
    public Division() {}
    public Division(String name, int studentCount){this.name=name;this.studentCount=studentCount;}
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public int getStudentCount(){return studentCount;} public void setStudentCount(int v){studentCount=v;}
}
