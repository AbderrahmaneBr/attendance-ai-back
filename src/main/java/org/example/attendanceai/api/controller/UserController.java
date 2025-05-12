package org.example.attendanceai.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.UserRequest;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.enums.UserRoles;
import org.example.attendanceai.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Tag(name = "User", description = "Operations related to user")
public class UserController {

    private final UserService userService;
//    @GetMapping
//    public ResponseEntity<String> getUser() {
//        return ResponseEntity.ok("Test test");
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest userDTO) {
//        User user = userService.save(userDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(user);
//    }
//
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
//    public ResponseEntity<User> updateUser(
//            @PathVariable long id,
//            @Valid @RequestBody UserRequest userDTO) {
//        return userService.update(id, userDTO)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        if (userService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> archiveUser(@PathVariable long id) {
        return userService.archive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> unarchiveUser(@PathVariable long id) {
        return userService.unarchive(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRoles role) {
        List<User> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUserProfile(Authentication authentication) {
        String username = authentication.getName();
        return userService.findByEmail(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}/promote-to-teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> promoteToTeacher(@PathVariable Long id) {
        return userService.promoteToTeacher(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/revoke-teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> revokeTeacher(@PathVariable Long id) {
        return userService.revokeTeacherRole(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
