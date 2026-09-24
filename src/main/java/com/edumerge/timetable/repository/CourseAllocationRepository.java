package com.edumerge.timetable.repository;
import com.edumerge.timetable.entity.CourseAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CourseAllocationRepository extends JpaRepository<CourseAllocation, Long> {}
