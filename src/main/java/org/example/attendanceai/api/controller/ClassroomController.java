package org.example.attendanceai.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.ClassroomRequest;
import org.example.attendanceai.api.response.ClassroomResponse;
import org.example.attendanceai.services.ClassroomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClassroomResponse>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponse> getClassroomById(@PathVariable long id) {
        Optional<ClassroomResponse> classroom = classroomService.findById(id);
        return classroom.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> createClassroom(@RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(classroomService.save(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> updateClassroom(@PathVariable long id, @RequestBody ClassroomRequest request) {
        Optional<ClassroomResponse> updated = classroomService.update(id, request);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClassroom(@PathVariable long id) {
        boolean deleted = classroomService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> archiveClassroom(@PathVariable long id) {
        Optional<ClassroomResponse> archived = classroomService.archive(id);
        return archived.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> unarchiveClassroom(@PathVariable long id) {
        Optional<ClassroomResponse> unarchived = classroomService.unarchive(id);
        return unarchived.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}