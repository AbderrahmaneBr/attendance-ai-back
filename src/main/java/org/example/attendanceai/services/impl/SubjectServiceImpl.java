package org.example.attendanceai.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.SubjectRequest;

import org.example.attendanceai.api.response.SubjectResponse;
import org.example.attendanceai.domain.entity.Subject;
import org.example.attendanceai.domain.entity.Teacher;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.mapper.SubjectMapper;
import org.example.attendanceai.domain.repository.SubjectRepository;
import org.example.attendanceai.domain.repository.TeacherRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Subject> findAll() {
        logger.debug("Finding all subjects");
        return subjectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subject> findById(long id) {
        logger.debug("Finding subject by id: {}", id);
        return subjectRepository.findById(id);
    }


//    @Override
//    @Transactional
//    public Subject save(SubjectRequest request) {
//        Subject subject = new Subject();
//        subject.setName(request.getName());
//        subject.setTeacher(
//                teacherRepository.findById(request.getTeacher().getId())
//                        .orElseThrow(() -> new RuntimeException("Teacher not found"))
//        );
//        subject.setArchived(false);
//        return subjectRepository.save(subject);
//    }

    @Override
    public SubjectResponse toResponse(Subject subject) {
        return subjectMapper.toResponse(subject);
    }
    @Override
    @Transactional
    public Subject save(SubjectRequest request) {
        Subject subject = subjectMapper.toEntity(request);
        subject.setArchived(false);
        subject.setCreatedAt(LocalDateTime.now());
        subject.setUpdatedAt(LocalDateTime.now());

        if (request.getTeacher() != null) {
            Teacher teacher = teacherRepository.findById(request.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            subject.setTeacher(teacher);
        }

        Subject savedSubject = subjectRepository.save(subject);
        return savedSubject;
    }

//@Override
//@Transactional
//public Optional<SubjectResponse> update(long id, SubjectRequest request) {
//    return subjectRepository.findById(id).map(subject -> {
//        subject.setName(request.getName());
//        subject.setArchived(request.getArchived());
//        subject.setUpdatedAt(LocalDateTime.now());
//
//        if (request.getTeacher() != null) {
//            Long teacherId = request.getTeacher().getId();
//            Teacher teacher = teacherRepository.findById(teacherId)
//                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
//            subject.setTeacher(teacher);
//        }
//
//        Subject saved = subjectRepository.save(subject);
//        return subjectMapper.toResponse(saved);
//    });
//}

    @Override
    public SubjectResponse updateSubject(Long id, SubjectRequest request) {
        // Fetch the existing subject by ID
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Update the subject
        subject.setName(request.getName());
        subject.setArchived(request.getArchived());
        subject.setUpdatedAt(LocalDateTime.now());

        // If the teacher exists, update the teacher relationship
        if (request.getTeacher() != null) {
            Teacher teacher = teacherRepository.findById(request.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            subject.setTeacher(teacher);
        }

        // Save the updated subject
        Subject savedSubject = subjectRepository.save(subject);

        // Convert to response and return
        return subjectMapper.toResponse(savedSubject);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        logger.debug("Deleting subject with id: {}", id);

        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Optional<Subject> archive(long id) {
        logger.debug("Archiving subject with id: {}", id);

        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setArchived(true);
                    return subjectRepository.save(subject);
                });
    }

    @Override
    @Transactional
    public Optional<Subject> unarchive(long id) {
        logger.debug("Unarchiving subject with id: {}", id);

        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setArchived(false);
                    return subjectRepository.save(subject);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findByTeacherId(long teacherId) {
        logger.debug("Finding subjects by teacher id: {}", teacherId);

        return subjectRepository.findByTeacherId(teacherId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findByNameContaining(String name) {
        logger.debug("Finding subjects with name containing: {}", name);

        return subjectRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTeacher(long subjectId, long userId) {
        logger.debug("Checking if user with id {} is teacher for subject with id {}", userId, subjectId);

        return subjectRepository.findById(subjectId)
                .map(subject -> subject.getTeacher() != null && subject.getTeacher().getId() == userId)
                .orElse(false);
    }





}
