package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupRequest {
    @NotBlank(message = "Group name is required")
    @Size(max = 100, message = "Group name must not exceed 100 characters")
    @Schema(description = "Group name")
    String name;

    @NotBlank(message = "Group study year is required")
    String study_year;

    @NotNull(message = "Department ID is required")
    @Schema(description = "Group's department id")
    Long departmentId;
}
