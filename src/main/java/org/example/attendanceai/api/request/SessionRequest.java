package org.example.attendanceai.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.attendanceai.domain.enums.PresenceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessionRequest {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startHour;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endHour;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Classroom ID is required")
    private Long classroomId;

}
