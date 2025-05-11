package org.example.attendanceai.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    LocalDate date;
    @NotNull
    LocalTime startHour;
    @NotNull
    LocalTime endHour;

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
