package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Presence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
}
