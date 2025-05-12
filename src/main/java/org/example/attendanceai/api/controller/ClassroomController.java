package org.example.attendanceai.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.ClassroomRequest;
import org.example.attendanceai.api.response.ClassroomResponse;
import org.example.attendanceai.services.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
@AllArgsConstructor
@Tag(name = "Classroom", description = "Operations related to classroom")
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    public ResponseEntity<List<ClassroomResponse>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponse> getClassroomById(@PathVariable long id) {
        return classroomService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> createClassroom(@Valid @RequestBody ClassroomRequest request) {
        ClassroomResponse response = classroomService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> updateClassroom(
            @PathVariable long id,
            @Valid @RequestBody ClassroomRequest request) {
        return classroomService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteClassroom(@PathVariable long id) {
        if (classroomService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/archive")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> archiveClassroom(@PathVariable long id) {
        return classroomService.archive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ClassroomResponse> unarchiveClassroom(@PathVariable long id) {
        return classroomService.unarchive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
