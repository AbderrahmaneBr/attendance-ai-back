package org.example.attendanceai.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.TeacherRequest;
import org.example.attendanceai.api.response.TeacherResponse;
import org.example.attendanceai.domain.entity.Teacher;
import org.example.attendanceai.domain.mapper.TeacherMapper;
import org.example.attendanceai.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@AllArgsConstructor
@Tag(name = "Teacher", description = "Operations related to teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        List<Teacher> teachers = teacherService.findAll();
        return ResponseEntity.ok(teacherMapper.toResponseTeachers(teachers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable long id) {
        return teacherService.findById(id)
                .map(teacherMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<TeacherResponse> createTeacher(@Valid @RequestBody TeacherRequest requestTeacher) {
//        Teacher teacher = teacherService.save(requestTeacher);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(teacherMapper.toResponse(teacher));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(
            @PathVariable long id, @Valid @RequestBody TeacherRequest requestTeacher) {
        return teacherService.update(id, requestTeacher)
                .map(teacherMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeacher(@PathVariable long id) {
        if (teacherService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
