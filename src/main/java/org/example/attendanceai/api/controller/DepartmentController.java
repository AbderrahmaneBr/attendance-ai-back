package org.example.attendanceai.api.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.DepartmentRequest;
import org.example.attendanceai.api.response.DepartmentResponse;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.services.DepartmentService;
import org.example.attendanceai.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@AllArgsConstructor
@Tag(name = "Department", description = "Operations related to department")
public class DepartmentController {

        private final DepartmentService departmentService;
        private final UserService userService;

        @GetMapping
        public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
                return ResponseEntity.ok(departmentService.findAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable long id) {
                return departmentService.findById(id).map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest request) {
                DepartmentResponse response = departmentService.save(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PatchMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<DepartmentResponse> updateDepartment(
                @PathVariable long id,
                @Valid @RequestBody DepartmentRequest request) {
                return departmentService.update(id, request)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Void> deleteDepartment(@PathVariable long id) {
                if (departmentService.delete(id)) {
                        return ResponseEntity.noContent().build();
                }
                return ResponseEntity.notFound().build();
        }

        @PatchMapping("/{id}/archive")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<DepartmentResponse> archiveDepartment(@PathVariable long id) {
                return departmentService.archive(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @PatchMapping("/{id}/unarchive")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<DepartmentResponse> unarchiveDepartment(@PathVariable long id) {
                return departmentService.unarchive(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }
//
//        @GetMapping("/chief/{chiefId}")
//        public ResponseEntity<Department> getDepartmentByChiefId(@PathVariable long chiefId) {
//                return departmentService.findByChiefId(chiefId)
        // TODO: add finfbycheifId in services
        
//                        .map(ResponseEntity::ok)
//                        .orElse(ResponseEntity.notFound().build());
//        }

}
