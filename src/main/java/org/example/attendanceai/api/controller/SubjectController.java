package org.example.attendanceai.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SubjectController {
    @Autowired
    private final SubjectService subjectService;


    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable long id) {
        return subjectService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//todo a modifier apres ajout drs dto

//    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<Subject> createSubject(@Valid @RequestBody SubjectDTO subjectDTO) {
//        Subject subject = subjectService.save(subjectDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(subject);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_CHIEF') or @subjectService.isTeacher(#id, authentication.principal.id)")
//    public ResponseEntity<Subject> updateSubject(
//            @PathVariable long id,
//            @Valid @RequestBody SubjectDTO subjectDTO) {
//        return subjectService.update(id, subjectDTO)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

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
    public ResponseEntity<List<Subject>> getSubjectsByName(@PathVariable String name) {
        List<Subject> subjects = subjectService.findByNameContaining(name);
        return ResponseEntity.ok(subjects);
    }
}
