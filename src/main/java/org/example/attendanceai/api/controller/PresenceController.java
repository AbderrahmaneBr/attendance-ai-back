package org.example.attendanceai.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.request.PresenceRequest;
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
@Tag(name = "Presence", description = "Operations related to presence")
public class PresenceController {

    private final PresenceService presenceService;

    @GetMapping
    public ResponseEntity<List<PresenceResponse>> getAllPresence() {
        return ResponseEntity.ok(presenceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresenceResponse> getPresenceById(@PathVariable long id) {
        return presenceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PresenceResponse> createPresence(@Valid @RequestBody PresenceRequest request) {
        PresenceResponse response = presenceService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PresenceResponse> updatePresence(
            @PathVariable long id,
            @Valid @RequestBody PresenceRequest request) {
        return presenceService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deletePresence(@PathVariable long id) {
        if (presenceService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/archive")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PresenceResponse> archivePresence(@PathVariable long id) {
        return presenceService.archive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PresenceResponse> unarchivePresence(@PathVariable long id) {
        return presenceService.unarchive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
