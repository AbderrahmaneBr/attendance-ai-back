package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Teacher;
import org.example.attendanceai.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUser(User user);
    boolean existsByUser(User user);
}
