package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
