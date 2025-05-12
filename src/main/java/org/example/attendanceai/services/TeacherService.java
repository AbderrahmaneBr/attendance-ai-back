package org.example.attendanceai.services;

import org.example.attendanceai.api.request.TeacherRequest;
import org.example.attendanceai.domain.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> findAll();
    Optional<Teacher> findById(Long id);
    Teacher save(TeacherRequest requestTeacher);
    Optional<Teacher> update(Long id, TeacherRequest requestTeacher);
    boolean deleteById(Long id);
    Optional<Teacher> findByUserId(Long userId);

}
