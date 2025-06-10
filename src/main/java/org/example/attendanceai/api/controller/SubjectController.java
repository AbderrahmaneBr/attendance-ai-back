package org.example.attendanceai.api.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.SubjectRequest;
import org.example.attendanceai.api.response.SubjectResponse;
import org.example.attendanceai.domain.entity.Subject;
import org.example.attendanceai.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@AllArgsConstructor
@Tag(name = "Subject", description = "Operations related to subject")
public class SubjectController {
    @Autowired
    private final SubjectService subjectService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable long id) {
        return subjectService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponse> createSubject(@Valid @RequestBody SubjectRequest request) {
        Subject subject = subjectService.save(request);
        SubjectResponse response = subjectService.toResponse(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') or @subjectService.isTeacher(#id, authentication.principal.id)")
    public ResponseEntity<SubjectResponse> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.updateSubject(id, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteSubject(@PathVariable long id) {
        if (subjectService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/archive")
    @PreAuthorize("hasAnyRole('ADMIN') or @subjectService.isTeacher(#id, authentication.principal.id)")
    public ResponseEntity<Subject> archiveSubject(@PathVariable long id) {
        return subjectService.archive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
    @PreAuthorize("hasAnyRole('ADMIN') or @subjectService.isTeacher(#id, authentication.principal.id)")
    public ResponseEntity<Subject> unarchiveSubject(@PathVariable long id) {
        return subjectService.unarchive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Subject>> getSubjectsByTeacherId(@PathVariable long teacherId) {
        List<Subject> subjects = subjectService.findByTeacherId(teacherId);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Subject>> getSubjectsByName(@PathVariable String name) {
        List<Subject> subjects = subjectService.findByNameContaining(name);
        return ResponseEntity.ok(subjects);
    }
}
