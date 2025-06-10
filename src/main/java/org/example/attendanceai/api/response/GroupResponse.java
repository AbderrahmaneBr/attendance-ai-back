package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class GroupResponse {
    Long id;
    String name;
    DepartmentResponse department;
    String study_year;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
