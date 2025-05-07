package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
