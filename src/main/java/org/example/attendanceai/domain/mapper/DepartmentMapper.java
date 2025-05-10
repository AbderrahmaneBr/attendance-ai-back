package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.DepartmentRequest;
import org.example.attendanceai.api.response.DepartmentResponse;
import org.example.attendanceai.api.response.UserResponse;
import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentResponse toResponse(Department department);
    Department toEntity(DepartmentResponse response);
    Department toEntity(DepartmentRequest request);
}