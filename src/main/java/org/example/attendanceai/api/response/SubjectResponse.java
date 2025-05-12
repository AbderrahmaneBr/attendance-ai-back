package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SubjectResponse {
    Long id;
    String name;
    TeacherResponse teacher;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
