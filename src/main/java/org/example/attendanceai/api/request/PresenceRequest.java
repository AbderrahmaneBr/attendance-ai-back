package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.example.attendanceai.domain.enums.PresenceStatus;

@Data
public class  PresenceRequest {

    @NotNull(message = "Student ID is required")
    @Schema(description = "Concerned Student id")
    private Long studentId;

    @NotNull(message = "Session ID is required")
    @Schema(description = "Concerned Session id")
    private Long sessionId;

    @NotNull(message = "Presence Status is required")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Presence status 'PRESENT', 'ABSENT', 'JUSTIFIED_ABSENCE'")
    private PresenceStatus status;

    @Nullable
    @Schema(description = "Presence description (optional)")
    private String description;

}
