package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.request.GroupRequest;
import org.example.attendanceai.api.response.GroupResponse;
import org.example.attendanceai.domain.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupResponse toResponse(Group group);
    Group toEntity(GroupResponse response);
    Group toEntity(GroupRequest request);
}
