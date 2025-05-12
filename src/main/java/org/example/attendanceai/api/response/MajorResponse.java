package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class MajorResponse {
    Long id;
    String name;
    DepartmentResponse department;
    UserResponse chief;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}