package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.StudentRequest;
import org.example.attendanceai.api.request.SubjectRequest;
import org.example.attendanceai.api.response.StudentResponse;
import org.example.attendanceai.api.response.SubjectResponse;
import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.entity.Subject;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentResponse toResponse(Student student);
    Student toEntity(StudentResponse response);
    Student toEntity(StudentRequest request);
}

