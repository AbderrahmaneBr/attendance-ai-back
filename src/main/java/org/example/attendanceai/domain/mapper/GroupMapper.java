package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.GroupRequest;
import org.example.attendanceai.api.response.GroupResponse;
import org.example.attendanceai.domain.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupResponse toResponse(Group group);
    Group toEntity(GroupResponse response);
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "studyYear", source = "studyYear")
    Group toEntity(GroupRequest request);
}
