package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
