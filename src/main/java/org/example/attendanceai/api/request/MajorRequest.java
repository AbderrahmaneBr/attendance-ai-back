package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class MajorRequest {

    @NotBlank(message = "Major name is required")
    @Size(max = 100, message = "Major name must not exceed 100 characters")
    @Schema(description = "Major name")
    private String name;

    @NotNull(message = "Department ID is required")
    @Schema(description = "Major's Department id")
    private Long departmentId;

    @NotNull(message = "Chief ID is required")
    @Schema(description = "Major's Chief id")
    private Long chiefId;

}