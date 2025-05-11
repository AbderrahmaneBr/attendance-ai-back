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
    long id;
    UserResponse user;

    private List<SubjectResponse> subjects;
}
