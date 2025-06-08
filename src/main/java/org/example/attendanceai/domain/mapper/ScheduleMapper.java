package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.PresenceRequest;
import org.example.attendanceai.api.request.ScheduleRequest;
import org.example.attendanceai.api.response.PresenceResponse;
import org.example.attendanceai.api.response.ScheduleResponse;
import org.example.attendanceai.domain.entity.Presence;
import org.example.attendanceai.domain.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule toEntity(ScheduleRequest request);
    ScheduleResponse toResponse(Schedule entity);
}
