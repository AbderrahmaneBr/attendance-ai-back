package org.example.attendanceai.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.ScheduleRequest;
import org.example.attendanceai.api.response.ScheduleResponse;
import org.example.attendanceai.domain.entity.Group;
import org.example.attendanceai.domain.entity.Schedule;
import org.example.attendanceai.domain.entity.Subject;
import org.example.attendanceai.domain.mapper.ScheduleMapper;
import org.example.attendanceai.domain.repository.GroupRepository;
import org.example.attendanceai.domain.repository.ScheduleRepository;
import org.example.attendanceai.services.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final GroupRepository groupRepository;

    @Override
    public ScheduleResponse create(ScheduleRequest request) {
        Schedule schedule = scheduleMapper.toEntity(request);

        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found"));
            schedule.setGroup(group);
        }

        if(request.getDetails() != null) {
            schedule.setDetails(request.getDetails());
        }

        return scheduleMapper.toResponse(scheduleRepository.save(schedule));
    }

    @Override
    public ScheduleResponse update(Long id, ScheduleRequest request) {
        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        Schedule updated = scheduleMapper.toEntity(request);
        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found"));
            updated.setGroup(group);
        }
        updated.setId(id);
        updated.setCreatedAt(existing.getCreatedAt()); // preserve original creation time

        return scheduleMapper.toResponse(scheduleRepository.save(updated));
    }

    @Override
    public ScheduleResponse getById(Long id) {
        return scheduleRepository.findById(id)
                .map(scheduleMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
    }

    @Override
    public List<ScheduleResponse> getAll() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public void archive(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        schedule.setArchived(true);
        scheduleRepository.save(schedule);
    }

    @Override
    public void unarchive(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        schedule.setArchived(false);
        scheduleRepository.save(schedule);
    }
}
