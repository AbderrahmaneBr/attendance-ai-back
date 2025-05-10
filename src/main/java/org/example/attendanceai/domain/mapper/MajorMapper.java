package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.response.DepartmentResponse;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.domain.entity.Major;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MajorMapper {
    MajorResponse toResponse(Major major);
    Major toEntity(MajorResponse response);
    Major toEntity(MajorRequest request);
}