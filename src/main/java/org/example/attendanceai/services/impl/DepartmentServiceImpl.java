package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.DepartmentRequest;
import org.example.attendanceai.api.response.DepartmentResponse;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.mapper.DepartmentMapper;
import org.example.attendanceai.domain.mapper.UserMapper;
import org.example.attendanceai.domain.repository.DepartmentRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DepartmentResponse save(DepartmentRequest request) {
        User user = userRepository.findById(request.getChiefId())
                .orElseThrow(() -> new RuntimeException("Chief not found"));

        Department department = departmentMapper.toEntity(request);
        department.setChief(user);
        department.setArchived(false);
        departmentRepository.save(department);
        DepartmentResponse response = new DepartmentResponse(
                department.getId(),
                department.getName(),
                userMapper.toResponse(department.getChief()),
                department.getArchived(),
                department.getCreatedAt(),
                department.getUpdatedAt()
        );

        return response;
    }

    @Override
    @Transactional
    public Optional<DepartmentResponse> update(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        department.setName(request.getName());
        department.setChief(userRepository.findById(request.getChiefId()).orElse(null));
        department.setArchived(request.getArchived());

        Department updated = departmentRepository.save(department);
        DepartmentResponse response = departmentMapper.toResponse(updated);

        return Optional.of(response);
    }


    @Override
    @Transactional
    public boolean delete(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<DepartmentResponse> archive(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        if (department != null) {
            department.setArchived(true);
            DepartmentResponse response = departmentMapper.toResponse(departmentRepository.save(department));
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public Optional<DepartmentResponse> unarchive(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        if (department != null) {
            department.setArchived(false);
            DepartmentResponse response = departmentMapper.toResponse(departmentRepository.save(department));
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentResponse> findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        DepartmentResponse response = new DepartmentResponse(department.getId(), department.getName(), userMapper.toResponse(department.getChief()), department.getArchived(), department.getCreatedAt(), department.getUpdatedAt());
        return Optional.of(response);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> new DepartmentResponse(department.getId(), department.getName(), userMapper.toResponse(department.getChief()), department.getArchived(), department.getCreatedAt(), department.getUpdatedAt()))
                .collect(Collectors.toList());
    }
}
