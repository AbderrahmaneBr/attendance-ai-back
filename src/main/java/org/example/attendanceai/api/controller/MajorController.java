package org.example.attendanceai.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.services.MajorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/majors")
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;


    @GetMapping
    public ResponseEntity<List<Major>> getAllMajors() {
        return ResponseEntity.ok(majorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Major> getMajorById(@PathVariable long id) {
        return majorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Major> createMajor(@Valid @RequestBody Major major) {
        Major majorS = majorService.save(major);
        return ResponseEntity.status(HttpStatus.CREATED).body(majorS);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Major> updateMajor(
            @PathVariable long id,
            @Valid @RequestBody Major major) {
        return majorService.update(id, major)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_CHIEF')")
    public ResponseEntity<Void> deleteMajor(@PathVariable long id) {
        if (majorService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/archive")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_CHIEF')")
    public ResponseEntity<Major> archiveMajor(@PathVariable long id) {
        return majorService.archive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_CHIEF')")
    public ResponseEntity<Major> unarchiveMajor(@PathVariable long id) {
        return majorService.unarchive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Optional<Major>> getMajorsByDepartmentId(@PathVariable long departmentId) {
        Optional<Major> majors = majorService.findByDepartmentId(departmentId);
        return ResponseEntity.ok(majors);
    }
//
//    @GetMapping("/chief/{chiefId}")
//    public ResponseEntity<Major> getMajorByChiefId(@PathVariable long chiefId) {
//        return majorService.findByChiefId(chiefId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}
