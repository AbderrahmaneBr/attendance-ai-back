package org.example.attendanceai.api.request.facedetection;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaunchResultRequest {
    @NotBlank(message = "Detection status is required")
    @Schema(description = "Detection status")
    String status;
    @NotEmpty(message = "Output list cannot be empty")
    List<FaceDetectionPresenceResponse> output;
    @NotNull(message = "Session ID is required")
    @Schema(description = "Session id")
    String session_id;
}


//Example:
//{
//    "session_id": Long,
//    "status": "done",
//    "output": [
//     {
//                "student_id": Long,
//                "presence": true
//     }]
//}
//
