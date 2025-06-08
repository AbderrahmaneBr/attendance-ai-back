package org.example.attendanceai.services;

import org.example.attendanceai.api.request.PresenceRequest;
import org.example.attendanceai.api.response.PresenceResponse;
import org.example.attendanceai.domain.entity.Presence;
import org.example.attendanceai.domain.enums.PresenceStatus;

import java.util.List;
import java.util.Optional;

public interface PresenceService {
    List<PresenceResponse> findAll();
    Optional<PresenceResponse> findById(long id);
    PresenceResponse save(PresenceRequest request);
    Optional<PresenceResponse> update(long id, PresenceRequest request);
    void markPresence(long id, PresenceStatus status);
    boolean deleteById(long id);
    Optional<PresenceResponse> archive(long id);
    Optional<PresenceResponse> unarchive(long id);
}

