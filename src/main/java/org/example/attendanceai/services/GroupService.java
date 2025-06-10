package org.example.attendanceai.services;

import org.example.attendanceai.api.request.GroupRequest;
import org.example.attendanceai.api.response.GroupResponse;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<GroupResponse> findAll();
    Optional<GroupResponse> findById(long id);
    GroupResponse save(GroupRequest request);
    Optional<GroupResponse> update(long id, GroupRequest request);
    boolean deleteById(long id);
    Optional<GroupResponse> archive(long id);
    Optional<GroupResponse> unarchive(long id);
}
