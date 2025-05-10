package org.example.attendanceai.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    LocalDateTime date;
    @NotNull
    String startHour;
    @NotNull
    String endHour;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    Classroom classroom;

    @Builder.Default
    Boolean archived = false;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
