package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.ClassroomRequest;
import org.example.attendanceai.api.response.ClassroomResponse;
import org.example.attendanceai.api.response.SessionResponse;
import org.example.attendanceai.domain.entity.*;
import org.example.attendanceai.domain.mapper.ClassroomMapper;
import org.example.attendanceai.domain.repository.ClassroomRepository;
import org.example.attendanceai.domain.repository.DepartmentRepository;
import org.example.attendanceai.services.ClassroomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<ClassroomResponse> findAll() {
        return classroomRepository.findAll().stream().map(classroomMapper::toResponse).collect(Collectors.toList());

    }

    @Override
    public Optional<ClassroomResponse> findById(long id) {
        return Optional.of(classroomMapper.toResponse(classroomRepository.findById(id).orElseThrow(() -> new RuntimeException("Classroom Not Found"))));

    }

    @Override
    public ClassroomResponse save(ClassroomRequest request) {
        Classroom classroom = classroomMapper.toEntity(request);

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            classroom.setDepartment(department);
        }

        Classroom savedClassroom = classroomRepository.save(classroom);

        return classroomMapper.toResponse(savedClassroom);
    }

    @Override
    public Optional<ClassroomResponse> update(long id, ClassroomRequest request) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new RuntimeException("Classroom Not Found"));

        if (request.getName() != null) {
            classroom.setName(request.getName());
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            classroom.setDepartment(department);
        }

        Classroom savedClassroom = classroomRepository.save(classroom);
        ClassroomResponse response = classroomMapper.toResponse(savedClassroom);

        return Optional.of(response);
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
        // Find Record
        Classroom classroomQuery = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        classroomQuery.setArchived(true);
        Classroom savedClassroom = classroomRepository.save(classroomQuery);
        return Optional.of(classroomMapper.toResponse(savedClassroom));
    }

    @Override
    public Optional<ClassroomResponse> unarchive(long id) {
        // Find Record
        Classroom classroomQuery = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        classroomQuery.setArchived(false);
        Classroom savedClassroom = classroomRepository.save(classroomQuery);
        return Optional.of(classroomMapper.toResponse(savedClassroom));
    }
}
