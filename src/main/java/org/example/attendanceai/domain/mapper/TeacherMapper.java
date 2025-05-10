package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.SubjectRequest;
import org.example.attendanceai.api.request.TeacherRequest;
import org.example.attendanceai.api.response.SubjectResponse;
import org.example.attendanceai.api.response.TeacherResponse;
import org.example.attendanceai.domain.entity.Subject;
import org.example.attendanceai.domain.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponse toResponse(Teacher teacher);
    Teacher toEntity(TeacherResponse response);
    Teacher toEntity(TeacherRequest request);
}
