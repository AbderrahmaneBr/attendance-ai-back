package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.response.DepartmentResponse;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.mapper.DepartmentMapper;
import org.example.attendanceai.domain.mapper.MajorMapper;
import org.example.attendanceai.domain.mapper.UserMapper;
import org.example.attendanceai.domain.repository.DepartmentRepository;
import org.example.attendanceai.domain.repository.MajorRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.MajorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final DepartmentMapper departmentMapper;
    private final MajorMapper majorMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MajorResponse> findAll() {
        return majorRepository.findAll().stream().map(major -> new MajorResponse(major.getId(), major.getName(), departmentMapper.toResponse(major.getDepartment()), userMapper.toResponse(major.getChief()), major.getArchived(), major.getCreatedAt(), major.getUpdatedAt())).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MajorResponse> findById(long id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major not found"));
        return Optional.of(new MajorResponse(major.getId(), major.getName(), departmentMapper.toResponse(major.getDepartment()), userMapper.toResponse(major.getChief()), major.getArchived(), major.getCreatedAt(), major.getUpdatedAt()));
    }

    @Override
    @Transactional
    public MajorResponse save(MajorRequest request) {
        Major major = majorMapper.toEntity(request);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        major.setDepartment(department);

        User chief = userRepository.findById(request.getChiefId())
                .orElseThrow(() -> new IllegalArgumentException("Chief user not found"));
        major.setChief(chief);
        major.setArchived(false);

        majorRepository.save(major);

        MajorResponse response = new MajorResponse(
                major.getId(), major.getName(), departmentMapper.toResponse(major.getDepartment()), userMapper.toResponse(major.getChief()), major.getArchived(), major.getCreatedAt(), major.getUpdatedAt()
        );

        return response;
    }

    @Override
    public Optional<MajorResponse> update(long id, MajorRequest request) {
        Major major = majorMapper.toEntity(request);
        return majorRepository.findById(id).map(existingMajor -> {
            // Update basic fields
            if (major.getName() != null) {
                existingMajor.setName(major.getName());
            }

            // Update department relationship
            if (major.getDepartment() != null) {
                Department department = departmentRepository.findById(major.getDepartment().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Department not found"));
                existingMajor.setDepartment(department);
            }

            // Update chief relationship
            if (major.getChief() != null) {
                User chief = userRepository.findById(major.getChief().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Chief user not found"));
                existingMajor.setChief(chief);
            }

            Major savedMajor = majorRepository.save(existingMajor);
            MajorResponse response = new MajorResponse(
                    savedMajor.getId(), savedMajor.getName(), departmentMapper.toResponse(savedMajor.getDepartment()), userMapper.toResponse(savedMajor.getChief()), savedMajor.getArchived(), savedMajor.getCreatedAt(), savedMajor.getUpdatedAt()
            );

            return response;
        });
    }

    @Override
    public boolean deleteById(long id) {
        if (majorRepository.existsById(id)) {
            majorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MajorResponse> archive(long id) {
        Major major = majorRepository.findById(id).orElseThrow(() -> new RuntimeException("Major not found"));

        if (major != null) {
            major.setArchived(true);
            Major savedMajor = majorRepository.save(major);
            MajorResponse response = new MajorResponse(
                    savedMajor.getId(), savedMajor.getName(), departmentMapper.toResponse(savedMajor.getDepartment()), userMapper.toResponse(savedMajor.getChief()), savedMajor.getArchived(), savedMajor.getCreatedAt(), savedMajor.getUpdatedAt()
            );
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public Optional<MajorResponse> unarchive(long id) {
        Major major = majorRepository.findById(id).orElseThrow(() -> new RuntimeException("Major not found"));

        if (major != null) {
            major.setArchived(false);
            Major savedMajor = majorRepository.save(major);
            MajorResponse response = new MajorResponse(
                    savedMajor.getId(), savedMajor.getName(), departmentMapper.toResponse(savedMajor.getDepartment()), userMapper.toResponse(savedMajor.getChief()), savedMajor.getArchived(), savedMajor.getCreatedAt(), savedMajor.getUpdatedAt()
            );
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MajorResponse> findByDepartmentId(long departmentId) {
        Major major = majorRepository.findByDepartmentId(departmentId).orElseThrow(() -> new RuntimeException("Major not found"));
        return Optional.of(new MajorResponse(major.getId(), major.getName(), departmentMapper.toResponse(major.getDepartment()), userMapper.toResponse(major.getChief()), major.getArchived(), major.getCreatedAt(), major.getUpdatedAt()));
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Optional<Major> findByChiefId(long chiefId) {
//        return majorRepository.findByChiefId(chiefId);
//    }
}

