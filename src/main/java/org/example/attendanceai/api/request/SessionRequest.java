package org.example.attendanceai.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.attendanceai.domain.enums.PresenceStatus;
import org.example.attendanceai.domain.enums.SessionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessionRequest {

    @NotNull(message = "Date is required")
    @Schema(description = "Date")
    private LocalDate date;

    @NotNull(message = "Start Hour is required")
    @Schema(description = "Start Hour")
    private LocalTime startHour;

    @NotNull(message = "End Hour is required")
    @Schema(description = "End Hour")
    private LocalTime endHour;

    @NotNull(message = "Classroom ID is required")
    @Schema(description = "Concerned Classroom id")
    private Long classroomId;

    @NotNull(message = "Subject ID is required")
    @Schema(description = "Concerned Subject id")
    private Long subjectId;

    @NotNull(message = "Schedule ID is required")
    @Schema(description = "Concerned Schedule id")
    private Long scheduleId;

    @NotNull(message = "Group ID is required")
    @Schema(description = "Concerned Group id")
    private Long groupId;

    @NotNull(message = "Status is required")
    @Schema(description = "Session's status id")
    private SessionStatus status = SessionStatus.EMPTY;

}
