package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ClassroomResponse {
    Long id;
    String name;
    Long camera_id;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
