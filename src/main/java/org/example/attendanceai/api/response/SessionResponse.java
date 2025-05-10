package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SessionResponse {
    long id;
    LocalDateTime date;
    String startHour;
    String endHour;
    SubjectResponse subject;
    TeacherResponse teacher;
    ClassroomResponse classroom;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
