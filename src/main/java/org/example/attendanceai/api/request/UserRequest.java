package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "User email is required")
    @Schema(description = "User's email")
    private String email;

    @NotBlank(message = "User password is required")
    @Schema(description = "User's password")
    private String password;
}
