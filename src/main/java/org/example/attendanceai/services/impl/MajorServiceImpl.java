package org.example.attendanceai.services.impl;

import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.repository.DepartmentRepository;
import org.example.attendanceai.domain.repository.MajorRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.MajorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public MajorServiceImpl(MajorRepository majorRepository,
                            DepartmentRepository departmentRepository,
                            UserRepository userRepository) {
        this.majorRepository = majorRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Major> findAll() {
        return majorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Major> findById(long id) {
        return majorRepository.findById(id);
    }

    @Override
    public Major save(Major major) {
        // Validate department exists if provided .getDepartment().getId() != null
        if (major.getDepartment() != null) {
            Department department = departmentRepository.findById(major.getDepartment().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            major.setDepartment(department);
        }

        // Validate chief exists if provided
        if (major.getChief() != null) {
            User chief = userRepository.findById(major.getChief().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Chief user not found"));
            major.setChief(chief);
        }

        return majorRepository.save(major);
    }

    @Override
    public Optional<Major> update(long id, Major majorDetails) {
        return majorRepository.findById(id).map(existingMajor -> {
            // Update basic fields
            if (majorDetails.getName() != null) {
                existingMajor.setName(majorDetails.getName());
            }

            // Update department relationship
            if (majorDetails.getDepartment() != null) {
                Department department = departmentRepository.findById(majorDetails.getDepartment().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Department not found"));
                existingMajor.setDepartment(department);
            }

            // Update chief relationship
            if (majorDetails.getChief() != null) {
                User chief = userRepository.findById(majorDetails.getChief().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Chief user not found"));
                existingMajor.setChief(chief);
            }

            return majorRepository.save(existingMajor);
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
    public Optional<Major> archive(long id) {
        return majorRepository.findById(id).map(major -> {
            major.setArchived(true);
            return majorRepository.save(major);
        });
    }

    @Override
    public Optional<Major> unarchive(long id) {
        return majorRepository.findById(id).map(major -> {
            major.setArchived(false);
            return majorRepository.save(major);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Major> findByDepartmentId(long departmentId) {
        return majorRepository.findById(departmentId);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Optional<Major> findByChiefId(long chiefId) {
//        return majorRepository.findByChiefId(chiefId);
//    }
}

