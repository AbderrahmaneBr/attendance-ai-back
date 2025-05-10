package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.ClassroomRequest;
import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.response.ClassroomResponse;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Classroom;
import org.example.attendanceai.domain.entity.Major;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomResponse toResponse(Classroom classroom);
    Classroom toEntity(ClassroomResponse response);
    Classroom toEntity(ClassroomRequest request);
}
