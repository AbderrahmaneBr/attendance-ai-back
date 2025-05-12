package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class PresenceResponse {
    Long id;
    StudentResponse student;
    SessionResponse session;
    String status;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}