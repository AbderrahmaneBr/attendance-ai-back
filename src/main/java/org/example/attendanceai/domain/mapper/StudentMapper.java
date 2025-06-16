package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.StudentRequest;
import org.example.attendanceai.api.request.SubjectRequest;
import org.example.attendanceai.api.response.StudentResponse;
import org.example.attendanceai.api.response.SubjectResponse;
import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.entity.Subject;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, MajorMapper.class})
public interface StudentMapper {

    @Mapping(target = "group", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "profile_img", target = "profile_img")
    Student toEntity(StudentRequest request);

    @Mapping(target = "group", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "profile_img", target = "profile_img")
    void updateStudentFromRequest(StudentRequest request, @MappingTarget Student student);

    StudentResponse toResponse(Student student);
}