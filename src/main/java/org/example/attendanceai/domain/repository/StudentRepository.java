package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.enums.StudyYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.major.id = :majorId")
    List<Student> findByMajorId(@Param("majorId") Long majorId);

    Optional<Student> findByCneId(Long cneId);

    List<Student> findByStudyYear(StudyYear studyYear);
    List<Student> findByGroup_StudyYear(StudyYear studyYear);
}
