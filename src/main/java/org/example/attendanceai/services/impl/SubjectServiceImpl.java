package org.example.attendanceai.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.attendanceai.domain.entity.Subject;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.repository.SubjectRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

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

    //todo a corriger apres ajout des dto
//    @Override
//    @Transactional
//    public Subject save(SubjectDTO subjectDTO) {
//        logger.debug("Saving new subject: {}", subjectDTO);
//
//        // Get the teacher from the repository
//        User teacher = userRepository.findById(subjectDTO.getTeacherId())
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "Teacher not found with id: " + subjectDTO.getTeacherId()));
//
//        Subject subject = new Subject();
//        subject.setName(subjectDTO.getName());
//        subject.setDescription(subjectDTO.getDescription());
//        subject.setCredits(subjectDTO.getCredits());
//        subject.setTeacher(teacher);
//        subject.setSemester(subjectDTO.getSemester());
//        subject.setArchived(false); // New subjects are not archived by default
//
//        return subjectRepository.save(subject);
//    }
//
//    @Override
//    @Transactional
//    public Optional<Subject> update(long id, SubjectDTO subjectDTO) {
//        logger.debug("Updating subject with id: {}", id);
//
//        return subjectRepository.findById(id)
//                .map(existingSubject -> {
//                    // Get the teacher from the repository
//                    User teacher = userRepository.findById(subjectDTO.getTeacherId())
//                            .orElseThrow(() -> new ResourceNotFoundException(
//                                    "Teacher not found with id: " + subjectDTO.getTeacherId()));
//
//                    existingSubject.setName(subjectDTO.getName());
//                    existingSubject.setDescription(subjectDTO.getDescription());
//                    existingSubject.setCredits(subjectDTO.getCredits());
//                    existingSubject.setTeacher(teacher);
//                    existingSubject.setSemester(subjectDTO.getSemester());
//
//                    return subjectRepository.save(existingSubject);
//                });
//    }

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
