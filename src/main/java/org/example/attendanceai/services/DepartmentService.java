package org.example.attendanceai.services;

import org.example.attendanceai.domain.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department save(Department department);
    Department update(Long id, Department department);
    boolean delete(Long id);
    Optional<Department> archive(Long id);
    Department findById(Long id);
    List<Department> findAll();
}
