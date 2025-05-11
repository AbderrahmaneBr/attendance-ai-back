package org.example.attendanceai.api.request;

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
    private Long studentId;

    @NotNull(message = "Session ID is required")
    private Long sessionId;

    @NotNull(message = "Presence Status is required")
    @Enumerated(EnumType.STRING)
    private PresenceStatus status;

    @Nullable
    private String description;

}
