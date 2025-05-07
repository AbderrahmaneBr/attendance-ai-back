package org.example.attendanceai.api.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String profileImg;
    private String role;
    private Boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

