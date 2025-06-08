package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClassroomRequest {
    @NotBlank(message = "Classroom name is required")
    @Size(max = 100, message = "Classroom name must not exceed 100 characters")
    @Schema(description = "Classroom name")
    String name;

    @NotBlank(message = "Classroom study year is required")
    String study_year;

    @NotNull(message = "Department ID is required")
    @Schema(description = "Classroom's department id")
    Long departmentId;
}
