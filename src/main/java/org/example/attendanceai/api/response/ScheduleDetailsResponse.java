package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ScheduleDetailsResponse {
    private Map<String, SessionSlotResponse> days;

    @Data
    @AllArgsConstructor
    public static class SessionSlotResponse {
        private Long subjectId;
        private Long classroomId;
        private LocalTime startHour;
        private LocalTime endHour;
    }
}