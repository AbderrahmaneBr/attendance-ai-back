package org.example.attendanceai.services;

import org.example.attendanceai.api.request.StudentRequest;
import org.example.attendanceai.api.response.StudentResponse;
import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.enums.StudyYear;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentResponse> findAll();
    Optional<StudentResponse> findById(long id);
    StudentResponse save(StudentRequest student);
    Optional<StudentResponse> update(long id, StudentRequest student);
    boolean deleteById(long id);
    StudentResponse archive(long id);
    StudentResponse unarchive(long id);
    List<StudentResponse> findByMajorId(long majorId);
    Optional<StudentResponse> findByCneId(String cneId);
    List<StudentResponse> findByStudyYear(StudyYear studyYear);
//    List<StudentResponse> findByGroupId(long id);

//    List<Student> search(String name, Long majorId, StudyYear studyYear);
}
