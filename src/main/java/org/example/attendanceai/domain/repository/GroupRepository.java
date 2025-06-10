package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
