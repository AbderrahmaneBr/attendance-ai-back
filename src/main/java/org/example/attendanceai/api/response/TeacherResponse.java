package org.example.attendanceai.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor

public class TeacherResponse {
    Long id;
    UserResponse user;

    private List<SubjectResponse> subjects;
}
