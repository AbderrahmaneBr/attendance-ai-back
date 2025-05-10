package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {
    @Query("SELECT m FROM Major m WHERE m.department.id = :departmentId")
    Optional<Major> findByDepartmentId(long departmentId);
}
