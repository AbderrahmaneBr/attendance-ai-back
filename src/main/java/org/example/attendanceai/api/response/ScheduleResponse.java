package org.example.attendanceai.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.attendanceai.domain.model.ScheduleDetails;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduleResponse {

    private Long id;

    private String name;

    @Schema(description = "Structured schedule details")
    private ScheduleDetails details;

    private Boolean archived;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
