package org.example.attendanceai.services;

import org.example.attendanceai.api.request.ScheduleRequest;
import org.example.attendanceai.api.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    ScheduleResponse create(ScheduleRequest request);

    ScheduleResponse update(Long id, ScheduleRequest request);

    ScheduleResponse getById(Long id);

    List<ScheduleResponse> getAll();

    void delete(Long id);

    void archive(Long id);

    void unarchive(Long id);
}