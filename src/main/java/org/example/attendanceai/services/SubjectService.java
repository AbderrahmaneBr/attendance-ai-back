package org.example.attendanceai.services;

import org.example.attendanceai.domain.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> findAll();
    Optional<Subject> findById(long id);
    //todo a modifier apres ajout des dtos
//    Subject save(SubjectDTO subjectDTO);
//    Optional<Subject> update(long id, SubjectDTO subjectDTO);
    boolean deleteById(long id);
    Optional<Subject> archive(long id);
    Optional<Subject> unarchive(long id);
    List<Subject> findByTeacherId(long teacherId);
    List<Subject> findByNameContaining(String name);
    boolean isTeacher(long subjectId, long userId);
}
