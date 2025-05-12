package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.attendanceai.api.response.TeacherResponse;

import java.time.LocalDateTime;

@Data
public class SubjectRequest {
    @NotBlank(message = "Subject name is required")
    @Schema(description = "Subject name")
    String name;

    @NotNull(message = "Teacher ID is required")
    @Schema(description = "Subject's Teacher id")
    Long teacherId;
}
