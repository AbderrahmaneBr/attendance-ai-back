package org.example.attendanceai.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank(message = "First name is required")
    @Schema(description = "Student's first name")
    private String firstname;

    @NotBlank(message = "Last name is required")
    @Schema(description = "Student's last name")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Student's unique email")
    private String email;

    @Nullable
    @Schema(description = "Optional phone number")
    private String phone;

    @Nullable
    @Schema(description = "Optional address")
    private String address;

    @NotBlank(message = "Profile image URL/path is required")
    @Schema(description = "URL or path to student's profile image")
    private String profileImg;

    @NotNull(message = "Major ID is required")
    @Schema(description = "ID of the student's major")
    private Long majorId;

    @NotNull(message = "Group ID is required")
    @Schema(description = "ID of the student's group")
    private Long groupId;

    @NotNull(message = "CNE ID is required")
    @Schema(description = "Unique CNE ID")
    private Long cneId;
}