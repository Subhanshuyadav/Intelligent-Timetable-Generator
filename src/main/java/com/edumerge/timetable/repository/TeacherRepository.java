package com.edumerge.timetable.repository;
import com.edumerge.timetable.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByNameIgnoreCase(String name);
}
