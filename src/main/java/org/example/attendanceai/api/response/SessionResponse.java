package org.example.attendanceai.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class SessionResponse {
    Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    LocalTime startHour;
    @JsonFormat(pattern = "HH:mm")
    LocalTime endHour;
    SubjectResponse subject;
    TeacherResponse teacher;
    ScheduleResponse schedule;
    GroupResponse group;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
