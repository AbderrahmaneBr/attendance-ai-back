package org.example.attendanceai.api.request;

import lombok.Data;
import org.example.attendanceai.api.response.TeacherResponse;

import java.time.LocalDateTime;

@Data
public class SubjectRequest {
    String name;
    //todo mieux ikoun idTeachear hna
    TeacherResponse teacher;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
