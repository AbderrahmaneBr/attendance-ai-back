package org.example.attendanceai.services.impl;

import org.example.attendanceai.api.request.PresenceRequest;
import org.example.attendanceai.api.response.PresenceResponse;
import org.example.attendanceai.services.PresenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresenceServiceImpl implements PresenceService {
    @Override
    public List<PresenceResponse> findAll() {
        return List.of();
    }

    @Override
    public Optional<PresenceResponse> findById(long id) {
        return Optional.empty();
    }

    @Override
    public PresenceResponse save(PresenceRequest request) {
        return null;
    }

    @Override
    public Optional<PresenceResponse> update(long id, PresenceRequest request) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public Optional<PresenceResponse> archive(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<PresenceResponse> unarchive(long id) {
        return Optional.empty();
    }
}
