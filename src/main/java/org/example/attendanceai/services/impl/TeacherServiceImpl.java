package org.example.attendanceai.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.TeacherRequest;
import org.example.attendanceai.domain.entity.Teacher;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.mapper.TeacherMapper;
import org.example.attendanceai.domain.repository.TeacherRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {


    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    @Transactional
    public Teacher save(TeacherRequest requestTeacher) {
        User user = userRepository.findById(requestTeacher.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestTeacher.getUserId()));

        Teacher teacher = teacherMapper.toEntity(requestTeacher, user);
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public Optional<Teacher> update(Long id, TeacherRequest requestTeacher) {
        if (!id.equals(requestTeacher.getUserId())) {
            throw new IllegalArgumentException("ID mismatch: path ID and request body user ID must match");
        }

        return teacherRepository.findById(id)
                .map(existingTeacher -> {
                    if (!existingTeacher.getUserId().equals(requestTeacher.getUserId())) {
                        throw new IllegalArgumentException("Cannot change teacher's user ID");
                    }

                    return existingTeacher;
                });
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Teacher> findByUserId(Long userId) {
        return teacherRepository.findByUserId(userId);
    }
}
