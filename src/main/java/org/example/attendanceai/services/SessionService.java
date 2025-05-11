package org.example.attendanceai.services;

import org.example.attendanceai.api.request.SessionRequest;
import org.example.attendanceai.api.response.SessionResponse;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    List<SessionResponse> findAll();
    Optional<SessionResponse> findById(long id);
    SessionResponse save(SessionRequest request);
    Optional<SessionResponse> update(long id, SessionRequest request);
    boolean deleteById(long id);
    Optional<SessionResponse> archive(long id);
    Optional<SessionResponse> unarchive(long id);
}
