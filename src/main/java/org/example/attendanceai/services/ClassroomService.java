package org.example.attendanceai.services;

import org.example.attendanceai.api.request.ClassroomRequest;
import org.example.attendanceai.api.response.ClassroomResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    List<ClassroomResponse> findAll();
    Optional<ClassroomResponse> findById(long id);
    ClassroomResponse save(ClassroomRequest request);
    Optional<ClassroomResponse> update(long id, ClassroomRequest request);
    boolean deleteById(long id);
    Optional<ClassroomResponse> archive(long id);
    Optional<ClassroomResponse> unarchive(long id);
}
