package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.SubjectRequest;
import org.example.attendanceai.api.request.TeacherRequest;
import org.example.attendanceai.api.response.SubjectResponse;
import org.example.attendanceai.api.response.TeacherResponse;
import org.example.attendanceai.domain.entity.Subject;
import org.example.attendanceai.domain.entity.Teacher;
import org.example.attendanceai.domain.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponse toResponse(Teacher teacher);
    Teacher toEntity(TeacherResponse response);
    Teacher toEntity(TeacherRequest request);
    Teacher toEntity(TeacherRequest requestTeacher, User user);
    List<TeacherResponse> toResponseTeachers(List<Teacher> teachers);
    }
