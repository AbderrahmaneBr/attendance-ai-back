package org.example.attendanceai.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.attendanceai.domain.enums.SessionStatus;
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

//    @NotNull
//    LocalDate date;
//    @NotNull
//    LocalTime startHour;
//    @NotNull
//    LocalTime endHour;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    Group group;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    Schedule schedule;

    @NotNull
    SessionStatus status;

    @Builder.Default
    Boolean archived = false;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
