package org.example.attendanceai.domain.mapper;

import org.example.attendanceai.api.response.UserResponse;
import org.example.attendanceai.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    User toEntity(UserResponse request);
}

