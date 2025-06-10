package org.example.attendanceai.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.StudentRequest;
import org.example.attendanceai.api.response.StudentResponse;
import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.mapper.StudentMapper;
import org.example.attendanceai.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        Student student = studentMapper.toEntity(request);
        Student savedStudent = studentService.save(student);
        return ResponseEntity.ok(studentMapper.toResponse(savedStudent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id) {
        Student student = studentService.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(studentMapper.toResponse(student));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<Student> students = studentService.findAll();
        List<StudentResponse> response = students.stream()
                .map(studentMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<StudentResponse> updateStudent(
//            @PathVariable Long id,
//            @Valid @RequestBody StudentRequest request) {
//        Student existingStudent = studentService.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
//        studentMapper.toEntity(request);
//        Student updatedStudent = studentService.save(existingStudent);
//        return ResponseEntity.ok(studentMapper.toResponse(updatedStudent));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
