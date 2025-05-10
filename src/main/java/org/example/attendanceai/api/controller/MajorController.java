package org.example.attendanceai.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.domain.mapper.MajorMapper;
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
    public ResponseEntity<List<MajorResponse>> getAllMajors() {
        return ResponseEntity.ok(majorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorResponse> getMajorById(@PathVariable long id) {
        return majorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MajorResponse> createMajor(@Valid @RequestBody MajorRequest request) {
        MajorResponse response = majorService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MajorResponse> updateMajor(
            @PathVariable long id,
            @Valid @RequestBody MajorRequest request) {
        return majorService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteMajor(@PathVariable long id) {
        if (majorService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/archive")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MajorResponse> archiveMajor(@PathVariable long id) {
        return majorService.archive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MajorResponse> unarchiveMajor(@PathVariable long id) {
        return majorService.unarchive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Optional<MajorResponse>> getMajorsByDepartmentId(@PathVariable long departmentId) {
        Optional<MajorResponse> response = majorService.findByDepartmentId(departmentId);
        return ResponseEntity.ok(response);
    }
//
//    @GetMapping("/chief/{chiefId}")
//    public ResponseEntity<Major> getMajorByChiefId(@PathVariable long chiefId) {
//        return majorService.findByChiefId(chiefId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}
