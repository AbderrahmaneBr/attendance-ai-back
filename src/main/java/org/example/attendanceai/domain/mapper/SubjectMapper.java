package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.SessionRequest;
import org.example.attendanceai.api.request.SubjectRequest;
import org.example.attendanceai.api.response.SessionResponse;
import org.example.attendanceai.api.response.SubjectResponse;
import org.example.attendanceai.domain.entity.Session;
import org.example.attendanceai.domain.entity.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectResponse toResponse(Subject subject);
    Subject toEntity(SubjectResponse response);
    Subject toEntity(SubjectRequest request);
}
