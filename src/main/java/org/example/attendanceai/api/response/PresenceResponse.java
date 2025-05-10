package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class PresenceResponse {
    long id;
    StudentResponse student;
    SessionResponse session;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}