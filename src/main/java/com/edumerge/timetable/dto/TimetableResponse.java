package com.edumerge.timetable.dto;
public record TimetableResponse(Long id,String division,String subject,String teacher,String room,int dayOfWeek,int periodNumber) {}
