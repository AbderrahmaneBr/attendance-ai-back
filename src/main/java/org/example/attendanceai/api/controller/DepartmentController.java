package org.example.attendanceai.api.controller;


import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.domain.entity.Department;
import org.example.attendanceai.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

        private final DepartmentService departmentService;

        @GetMapping
        public ResponseEntity<List<Department>> getAllDepartments() {
                return ResponseEntity.ok(departmentService.findAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {
                return ResponseEntity.ok(departmentService.findById(id));
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
                Department departmentS = departmentService.save(department);
                return ResponseEntity.status(HttpStatus.CREATED).body(departmentS);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Department> updateDepartment(
                @PathVariable long id,
                @Valid @RequestBody Department department) {
             return    ResponseEntity.ok(departmentService.update(id, department));
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
        public ResponseEntity<Department> archiveDepartment(@PathVariable long id) {
                return departmentService.archive(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

//        @PatchMapping("/{id}/unarchive")
//        @PreAuthorize("hasRole('ADMIN')")
//        public ResponseEntity<Department> unarchiveDepartment(@PathVariable long id) {
//                return departmentService.unarchive(id)
//                        .map(ResponseEntity::ok)
//                        .orElse(ResponseEntity.notFound().build());
//        }
//
//        @GetMapping("/chief/{chiefId}")
//        public ResponseEntity<Department> getDepartmentByChiefId(@PathVariable long chiefId) {
//                return departmentService.findByChiefId(chiefId)
        // TODO: add finfbycheifId in services
        
//                        .map(ResponseEntity::ok)
//                        .orElse(ResponseEntity.notFound().build());
//        }

}
