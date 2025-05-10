package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TeacherResponse {
    long id;
    UserResponse user;
}
