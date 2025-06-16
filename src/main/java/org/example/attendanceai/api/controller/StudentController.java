package org.example.attendanceai.api.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.attendanceai.api.request.StudentRequest;
import org.example.attendanceai.api.response.StudentResponse;
import org.example.attendanceai.domain.enums.StudyYear;
import org.example.attendanceai.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Operations related to student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse savedStudent = studentService.save(request);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id) {
        StudentResponse student = studentService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with ID: " + id));
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {
        StudentResponse updatedStudent = studentService.update(id, request)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with ID: " + id));
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponse> archiveStudent(@PathVariable Long id) {
        StudentResponse archivedStudent = studentService.archive(id);
        return ResponseEntity.ok(archivedStudent);
    }

    @PutMapping("/{id}/unarchive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponse> unarchiveStudent(@PathVariable Long id) {
        StudentResponse unarchivedStudent = studentService.unarchive(id);
        return ResponseEntity.ok(unarchivedStudent);
    }

    @GetMapping("/byMajor/{majorId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByMajor(@PathVariable long majorId) {
        List<StudentResponse> students = studentService.findByMajorId(majorId);
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/byCneId/{cneId}")
    public ResponseEntity<StudentResponse> getStudentByCneId(@PathVariable("cneId") String cneId) {
        StudentResponse student = studentService.findByCneId(cneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with CNE ID: " + cneId));
        return ResponseEntity.ok(student);
    }

    @GetMapping("/byStudyYear/{studyYear}")
    public ResponseEntity<List<StudentResponse>> getStudentsByStudyYear(@PathVariable StudyYear studyYear) {
        List<StudentResponse> students = studentService.findByStudyYear(studyYear);
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

//    @GetMapping("/byGroup/{groupId}")
//    public ResponseEntity<List<StudentResponse>> getStudentsByGroup(@PathVariable long groupId) {
//        List<StudentResponse> students = studentService.findByGroupId(groupId);
//        if (students.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(students);
//    }
}