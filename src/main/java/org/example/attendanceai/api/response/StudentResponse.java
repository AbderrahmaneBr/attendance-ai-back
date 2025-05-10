package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.attendanceai.domain.enums.StudyYear;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String profileImg;
    private MajorResponse major;
    private String cne_id;
    private String study_year;
    private Boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
