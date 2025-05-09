package org.example.attendanceai.domain.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.attendanceai.domain.enums.StudyYear;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    String firstname;
    @NotNull
    String lastname;
    @NotNull
    String email;
    @Nullable
    String phone;
    @Nullable
    String address;

    @Nullable
    String profile_img;

    @ManyToOne
    @JoinColumn(name = "major_id")
    Major major;

    //Todo abdo modified this to cneId
    @Column(unique = true)
    long cneId;

    @Enumerated(EnumType.STRING)
    StudyYear study_year;

    @Builder.Default
    Boolean archived = false;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
