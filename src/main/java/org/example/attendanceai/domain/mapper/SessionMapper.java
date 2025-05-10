package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.PresenceRequest;
import org.example.attendanceai.api.request.SessionRequest;
import org.example.attendanceai.api.response.PresenceResponse;
import org.example.attendanceai.api.response.SessionResponse;
import org.example.attendanceai.domain.entity.Presence;
import org.example.attendanceai.domain.entity.Session;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionResponse toResponse(Session session);
    Session toEntity(SessionResponse response);
    Session toEntity(SessionRequest request);
}
