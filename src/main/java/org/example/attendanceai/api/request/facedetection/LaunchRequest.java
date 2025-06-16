package org.example.attendanceai.api.request.facedetection;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaunchRequest {
    @NotNull(message = "Camera ID is required")
    @Schema(description = "Camera id")
    String camera_id;
    @NotEmpty(message = "Students list cannot be empty")
    List<FaceDetectionStudentResponse> students;
    @NotNull(message = "Session ID is required")
    @Schema(description = "Session id")
    String session_id;
    @NotNull(message = "Detection duration is required")
    @Schema(description = "Detection duration")
        Integer duration;
}

//Example:
//{
//    "camera_id": Long,
//    "students": [
//        {
//        "id": Long,
//        "image_path": "string"
//        }
//    ],
//    "session_id": "string",
//    "Duration": Integer
//}

