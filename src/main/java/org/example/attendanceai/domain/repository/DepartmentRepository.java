package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
