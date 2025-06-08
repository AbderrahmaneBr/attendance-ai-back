package org.example.attendanceai.domain.model;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ScheduleDetails {
    private Map<String, List<SessionSlot>> days;

    // Add a no-arg constructor for Jackson deserialization
    public ScheduleDetails() {
    }

    // Add explicit constructor for convenience
    public ScheduleDetails(Map<String, List<SessionSlot>> days) {
        this.days = days;
    }

    // Explicitly add getter and setter if Lombok's @Data is not resolving correctly
    public Map<String, List<SessionSlot>> getDays() {
        return days;
    }

    public void setDays(Map<String, List<SessionSlot>> days) {
        this.days = days;
    }

    @Data
    public static class SessionSlot {
        private Long subjectId;
        private Long classroomId;
        private LocalTime startHour;
        private LocalTime endHour;
    }
}