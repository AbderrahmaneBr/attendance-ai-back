package org.example.attendanceai.domain.model;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ScheduleDetails {
    private Map<String, List<SessionSlot>> days;

    @Data
    public static class SessionSlot {
        private String subject;
        private String classroom;
        private LocalTime startHour;
        private LocalTime endHour;
    }
}