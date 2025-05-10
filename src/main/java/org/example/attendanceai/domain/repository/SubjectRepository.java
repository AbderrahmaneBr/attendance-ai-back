package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByTeacherId(long teacherId);
    List<Subject> findByNameContainingIgnoreCase(String name);

}
