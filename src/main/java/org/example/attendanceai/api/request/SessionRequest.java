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

//    @JsonFormat(pattern = "dd-MM-yyyy")
//    @Schema(description = "Session date 'dd-MM-yyyy' format")
//    private LocalDate date;

//    @JsonFormat(pattern = "HH:mm")
//    @Schema(description = "Session start hour 'HH:mm' format")
//    private LocalTime startHour;
//
//    @JsonFormat(pattern = "HH:mm")
//    @Schema(description = "Session end hour 'HH:mm' format")
//    private LocalTime endHour;

    @NotNull(message = "Subject ID is required")
    @Schema(description = "Session's Subject id")
    private Long subjectId;

    @NotNull(message = "Schedule ID is required")
    @Schema(description = "Session's Schedule id")
    private Long scheduleId;

    @NotNull(message = "Status is required")
    @Schema(description = "Session's status id")
    private SessionStatus status = SessionStatus.NOT_FILLED;

    @NotNull(message = "Group ID is required")
    @Schema(description = "Session's Group id")
    private Long groupId;

}
