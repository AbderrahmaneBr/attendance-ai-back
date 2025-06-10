package org.example.attendanceai.domain.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.attendanceai.domain.enums.StudyYear;
import org.example.attendanceai.util.GenericEnumConverter;
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
    @Column(unique = true)
    String email;
    @Nullable
    @Column(unique = true)
    String phone;
    @Nullable
    String address;

    @NotNull
    String profile_img;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "major_id")
    Major major;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    Group group;

    @NotNull
    @Column(unique = true)
    long cneId;

    @Builder.Default
    Boolean archived = false;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
