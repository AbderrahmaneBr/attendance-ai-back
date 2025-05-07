package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {
}
