package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

}
