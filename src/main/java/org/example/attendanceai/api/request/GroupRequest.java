package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.attendanceai.domain.enums.StudyYear;

@Data
public class GroupRequest {
    @NotBlank(message = "Group name is required")
    @Size(max = 100, message = "Group name must not exceed 100 characters")
    @Schema(description = "Group name")
    String name;

    @NotNull(message = "Group study year is required")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Group study year 'FIRST, SECOND, THIRD, FOURTH, FIFTH'")
    StudyYear studyYear;

    @NotNull(message = "Department ID is required")
    @Schema(description = "Group's department id")
    Long departmentId;
}
