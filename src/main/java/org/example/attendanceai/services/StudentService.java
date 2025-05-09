package org.example.attendanceai.services;

import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.enums.StudyYear;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();
    Optional<Student> findById(long id);
    Student save(Student student);
    Optional<Student> update(long id, Student student);
    boolean deleteById(long id);
    Optional<Student> archive(long id);
    Optional<Student> unarchive(long id);
    List<Student> findByMajorId(long majorId);
    Optional<Student> findByCneId(long cneId);
//    List<Student> findByStudyYear(StudyYear studyYear);
//    List<Student> search(String name, Long majorId, StudyYear studyYear);
}
