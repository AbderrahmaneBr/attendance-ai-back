package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.ClassroomRequest;
import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.response.ClassroomResponse;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Classroom;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.mapper.ClassroomMapper;
import org.example.attendanceai.domain.repository.ClassroomRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.ClassroomService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;
    private final UserRepository userRepository;

    @Override
    public List<ClassroomResponse> findAll() {
        return classroomRepository.findAll().stream()
                .map(classroomMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClassroomResponse> findById(long id) {
        return classroomRepository.findById(id)
                .map(classroomMapper::toResponse);
    }

    @Override
    public ClassroomResponse save(ClassroomRequest request) {
        Classroom classroom = classroomMapper.toEntity(request);
        Classroom savedClassroom = classroomRepository.save(classroom);
        return classroomMapper.toResponse(savedClassroom);
    }

    @Override
    public Optional<ClassroomResponse> update(long id, ClassroomRequest request) {
        // Find user from context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return classroomRepository.findById(id).map(existingClassroom -> {
            // Allow update if ADMIN
            if (!user.getRole().name().equals("ADMIN")) {
                try {
                    throw new AccessDeniedException("You don't have permission to update this Classroom.");
                } catch (AccessDeniedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (request.getName() != null) {
                existingClassroom.setName(request.getName());
            }

            if (request.getCameraId() != null) {
                existingClassroom.setCamera_id(request.getCameraId());
            }

            Classroom updatedClassroom = classroomRepository.save(existingClassroom);
            return classroomMapper.toResponse(updatedClassroom);
        });
    }

    @Override
    public boolean deleteById(long id) {
        if (classroomRepository.existsById(id)) {
            classroomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ClassroomResponse> archive(long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return classroomRepository.findById(id).map(classroom -> {
            if (!user.getRole().name().equals("ADMIN")) {
                try {
                    throw new AccessDeniedException("You don't have permission to archive this Classroom.");
                } catch (AccessDeniedException e) {
                    throw new RuntimeException(e);
                }
            }

            classroom.setArchived(true);
            Classroom savedClassroom = classroomRepository.save(classroom);
            return classroomMapper.toResponse(savedClassroom);
        });
    }

    @Override
    public Optional<ClassroomResponse> unarchive(long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return classroomRepository.findById(id).map(classroom -> {
            if (!user.getRole().name().equals("ADMIN")) {
                try {
                    throw new AccessDeniedException("You don't have permission to unarchive this Classroom.");
                } catch (AccessDeniedException e) {
                    throw new RuntimeException(e);
                }
            }

            classroom.setArchived(false);
            Classroom savedClassroom = classroomRepository.save(classroom);
            return classroomMapper.toResponse(savedClassroom);
        });
    }
}
