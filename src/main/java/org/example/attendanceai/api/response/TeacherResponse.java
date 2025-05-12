package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TeacherResponse {
    Long id;
    UserResponse user;
}
