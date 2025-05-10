package org.example.attendanceai.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Teacher {

    @Id
    private Long userId;

    @NotNull
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public long getId() {
        return userId;
    }
}