package org.example.attendanceai.services;

import org.example.attendanceai.api.request.DepartmentRequest;
import org.example.attendanceai.api.response.DepartmentResponse;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentResponse save(DepartmentRequest request);
    Optional<DepartmentResponse> update(Long id, DepartmentRequest request);
    boolean delete(Long id);
    Optional<DepartmentResponse> archive(Long id);
    Optional<DepartmentResponse> unarchive(Long id);
    Optional<DepartmentResponse>  findById(Long id);
    List<DepartmentResponse> findAll();
}
