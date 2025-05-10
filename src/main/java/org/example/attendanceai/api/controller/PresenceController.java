package org.example.attendanceai.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.api.response.PresenceResponse;
import org.example.attendanceai.services.MajorService;
import org.example.attendanceai.services.PresenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/presence")
@AllArgsConstructor
public class PresenceController {

    private final PresenceService presenceService;

    @GetMapping
    public ResponseEntity<List<PresenceResponse>> getAllPresence() {
        return ResponseEntity.ok(presenceService.findAll());
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<MajorResponse> getMajorById(@PathVariable long id) {
//        return majorService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<MajorResponse> createMajor(@Valid @RequestBody MajorRequest request) {
//        MajorResponse response = majorService.save(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @PatchMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<MajorResponse> updateMajor(
//            @PathVariable long id,
//            @Valid @RequestBody MajorRequest request) {
//        return majorService.update(id, request)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<Void> deleteMajor(@PathVariable long id) {
//        if (majorService.deleteById(id)) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PatchMapping("/{id}/archive")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<MajorResponse> archiveMajor(@PathVariable long id) {
//        return majorService.archive(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PatchMapping("/{id}/unarchive")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<MajorResponse> unarchiveMajor(@PathVariable long id) {
//        return majorService.unarchive(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/department/{departmentId}")
//    public ResponseEntity<Optional<MajorResponse>> getMajorsByDepartmentId(@PathVariable long departmentId) {
//        Optional<MajorResponse> response = majorService.findByDepartmentId(departmentId);
//        return ResponseEntity.ok(response);
//    }
}
