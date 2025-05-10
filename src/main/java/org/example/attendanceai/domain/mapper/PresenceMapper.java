package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.PresenceRequest;
import org.example.attendanceai.api.response.PresenceResponse;
import org.example.attendanceai.api.response.UserResponse;
import org.example.attendanceai.domain.entity.Presence;
import org.example.attendanceai.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PresenceMapper {
    PresenceResponse toResponse(Presence presence);
    Presence toEntity(PresenceResponse response);
    Presence toEntity(PresenceRequest request);
}