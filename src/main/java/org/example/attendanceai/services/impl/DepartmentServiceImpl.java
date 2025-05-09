package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.domain.repository.DepartmentRepository;
import org.example.attendanceai.services.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public Department update(Long id, Department department) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setId(id);
        return departmentRepository.save(department);
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
    public Optional<Department> archive(Long id) {
//        Department department = departmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Department not found"));
//        department.setArchived(true);
//        departmentRepository.save(department);
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            department.setArchived(true);
            return Optional.of(departmentRepository.save(department));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
