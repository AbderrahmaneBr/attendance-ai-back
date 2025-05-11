package org.example.attendanceai.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class MajorRequest {

    @NotBlank(message = "Major name is required")
    @Size(max = 100, message = "Major name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Chief ID is required")
    private Long chiefId;

}