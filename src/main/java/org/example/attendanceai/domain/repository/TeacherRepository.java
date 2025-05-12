package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Teacher;
import org.example.attendanceai.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUser(User user);
    boolean existsByUser(User user);
    Optional<Teacher> findByUserId(Long userId);
}
