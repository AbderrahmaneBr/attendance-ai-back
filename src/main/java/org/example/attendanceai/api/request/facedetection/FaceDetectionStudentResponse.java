package org.example.attendanceai.api.request.facedetection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaceDetectionStudentResponse {
    String student_id;
    String image_path;
}
