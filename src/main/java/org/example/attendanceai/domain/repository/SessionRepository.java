package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
