package org.example.attendanceai.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.attendanceai.domain.enums.SessionStatus;
import org.example.attendanceai.domain.model.ScheduleDetails;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    @NotBlank(message = "Schedule name is required")
    @Size(max = 100, message = "Schedule name must not exceed 100 characters")
    @Schema(description = "Schedule name")
    String name;

    @NotNull(message = "Group ID is required")
    @Schema(description = "Concerned Group id")
    private Long groupId;

    @NotNull(message = "Schedule details are required")
    @Schema(description = "Structured schedule details as JSON")
    private ScheduleDetails details;
}
