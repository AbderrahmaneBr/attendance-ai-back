package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.GroupRequest;
import org.example.attendanceai.api.response.GroupResponse;
import org.example.attendanceai.domain.entity.*;
import org.example.attendanceai.domain.mapper.GroupMapper;
import org.example.attendanceai.domain.repository.GroupRepository;
import org.example.attendanceai.domain.repository.DepartmentRepository;
import org.example.attendanceai.services.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<GroupResponse> findAll() {
        return groupRepository.findAll().stream().map(groupMapper::toResponse).collect(Collectors.toList());

    }

    @Override
    public Optional<GroupResponse> findById(long id) {
        return Optional.of(groupMapper.toResponse(groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Classroom Not Found"))));

    }

    @Override
    public GroupResponse save(GroupRequest request) {
        Group group = groupMapper.toEntity(request);

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            group.setDepartment(department);
        }

        Group savedGroup = groupRepository.save(group);

        return groupMapper.toResponse(savedGroup);
    }

    @Override
    public Optional<GroupResponse> update(long id, GroupRequest request) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Classroom Not Found"));

        if (request.getName() != null) {
            group.setName(request.getName());
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            group.setDepartment(department);
        }

        Group savedGroup = groupRepository.save(group);
        GroupResponse response = groupMapper.toResponse(savedGroup);

        return Optional.of(response);
    }

    @Override
    public boolean deleteById(long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<GroupResponse> archive(long id) {
        // Find Record
        Group groupQuery = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        groupQuery.setArchived(true);
        Group savedGroup = groupRepository.save(groupQuery);
        return Optional.of(groupMapper.toResponse(savedGroup));
    }

    @Override
    public Optional<GroupResponse> unarchive(long id) {
        // Find Record
        Group groupQuery = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        groupQuery.setArchived(false);
        Group savedGroup = groupRepository.save(groupQuery);
        return Optional.of(groupMapper.toResponse(savedGroup));
    }
}
