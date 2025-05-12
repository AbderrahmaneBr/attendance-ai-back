package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.example.attendanceai.domain.entity.User;

@Data
public class DepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    @Schema(description = "Department name")
    private String name;

    @NotNull(message = "Chief ID is required")
    @Schema(description = "Department's Chief id")
    private Long chiefId;

}