package com.edumerge.timetable.entity;

import jakarta.persistence.*;

@Entity @Table(name="rooms", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class Room {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    private int capacity;
    @Column(nullable=false) private boolean lab;
    public Room() {}
    public Room(String name,int capacity,boolean lab){this.name=name;this.capacity=capacity;this.lab=lab;}
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public int getCapacity(){return capacity;} public void setCapacity(int v){capacity=v;}
    public boolean isLab(){return lab;} public void setLab(boolean v){lab=v;}
}
