package org.example.attendanceai.api.request.facedetection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceDetectionPresenceResponse {
    String student_id;
    Boolean present;
}