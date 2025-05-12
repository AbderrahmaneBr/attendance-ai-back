package org.example.attendanceai.api.response;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.attendanceai.domain.entity.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class DepartmentResponse {
    Long id;
    String name;
    UserResponse chief;
    Boolean archived;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
