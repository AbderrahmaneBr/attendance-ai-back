package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
