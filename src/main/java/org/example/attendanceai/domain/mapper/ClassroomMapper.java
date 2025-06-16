package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.ClassroomRequest;
import org.example.attendanceai.api.request.GroupRequest;
import org.example.attendanceai.api.response.ClassroomResponse;
import org.example.attendanceai.api.response.GroupResponse;
import org.example.attendanceai.domain.entity.Classroom;
import org.example.attendanceai.domain.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomResponse toResponse(Classroom classroom);
    Classroom toEntity(ClassroomResponse response);
    Classroom toEntity(ClassroomRequest request);
}
