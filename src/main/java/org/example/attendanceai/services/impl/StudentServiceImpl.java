package org.example.attendanceai.services.impl;

import org.example.attendanceai.api.request.StudentRequest;
import org.example.attendanceai.api.response.StudentResponse;
import org.example.attendanceai.domain.entity.Group;
import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.enums.StudyYear;
import org.example.attendanceai.domain.mapper.StudentMapper;
import org.example.attendanceai.domain.repository.GroupRepository;
import org.example.attendanceai.domain.repository.MajorRepository;
import org.example.attendanceai.domain.repository.StudentRepository;
import org.example.attendanceai.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final MajorRepository majorRepository;
    private final StudentMapper studentMapper;     // Inject StudentMapper


    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository, MajorRepository majorRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.majorRepository = majorRepository;
        this.studentMapper = studentMapper;
    }


        @Override
        @Transactional(readOnly = true)
        public List<StudentResponse> findAll() {
            return studentRepository.findAll().stream()
                    .map(studentMapper::toResponse)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<StudentResponse> findById(long id) { // Changed return type to Optional<StudentResponse>
            return studentRepository.findById(id)
                    .map(studentMapper::toResponse);
        }

        @Override
        @Transactional // Changed to accept StudentRequest and return StudentResponse
        public StudentResponse save(StudentRequest request) {
            // 1. Map DTO to entity (excluding complex objects)
            Student student = studentMapper.toEntity(request);

            // 2. Handle Group relationship
            if (request.getGroupId() != null) {
                Group group = groupRepository.findById(request.getGroupId())
                        .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + request.getGroupId()));
                student.setGroup(group);
            } else {
                // Depending on your validation, you might throw an error here if groupId is truly mandatory
                // or handle a default case. @NotNull in DTO should prevent null.
            }


            // 3. Handle Major relationship
            if (request.getMajorId() != null) {
                Major major = majorRepository.findById(request.getMajorId())
                        .orElseThrow(() -> new IllegalArgumentException("Major not found with ID: " + request.getMajorId()));
                student.setMajor(major);
            } else {
                // Similar handling for majorId as for groupId
            }

            // 4. Save the fully populated student entity
            Student savedStudent = studentRepository.save(student);

            // 5. Map the saved entity back to a response DTO
            return studentMapper.toResponse(savedStudent);
        }

        @Override
        @Transactional // Changed to accept StudentRequest and return Optional<StudentResponse>
        public Optional<StudentResponse> update(long id, StudentRequest request) { // Changed input parameter
            return studentRepository.findById(id).map(existingStudent -> {

                // Use MapStruct's update method to apply changes from request to existing entity
                // This handles updating simple fields (firstname, lastname, email, profile_img, phone, address, cneId)
                studentMapper.updateStudentFromRequest(request, existingStudent);

                // Handle Group relationship update
                if (request.getGroupId() != null) {
                    Group group = groupRepository.findById(request.getGroupId())
                            .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + request.getGroupId()));
                    existingStudent.setGroup(group);
                } else {
                    // If groupId is null in the request, you might want to nullify the group in the entity,
                    // or throw an error if group is mandatory.
                    // existingStudent.setGroup(null);
                    // Or throw new IllegalArgumentException("Group ID cannot be null for update.");
                }

                // Handle Major relationship update
                if (request.getMajorId() != null) {
                    Major major = majorRepository.findById(request.getMajorId())
                            .orElseThrow(() -> new IllegalArgumentException("Major not found with ID: " + request.getMajorId()));
                    existingStudent.setMajor(major);
                } else {
                    // Similar handling for majorId as for groupId
                }

                Student updatedStudent = studentRepository.save(existingStudent);
                return studentMapper.toResponse(updatedStudent); // Map updated entity to response DTO
            });
        }

        @Override
        @Transactional
        public boolean deleteById(long id) {
            if (studentRepository.existsById(id)) {
                studentRepository.deleteById(id);
                return true;
            }
            return false;
        }

        @Override
        @Transactional
        public StudentResponse archive(long id) {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));

            student.setArchived(true);
            Student archivedStudent = studentRepository.save(student);

            return studentMapper.toResponse(archivedStudent);
        }

        @Override
        @Transactional
        public StudentResponse unarchive(long id) {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));

            student.setArchived(false);
            Student archivedStudent = studentRepository.save(student);

            return studentMapper.toResponse(archivedStudent);
        }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> findByMajorId(long majorId) {
        // Fetch entities, then map each to a StudentResponse
        return studentRepository.findByMajorId(majorId).stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentResponse> findByCneId(String cneId) {
        // Fetch entity, then map if present. Optional will handle the absent case.
        return studentRepository.findByCneId(cneId)
                .map(studentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> findByStudyYear(StudyYear studyYear) {
        // Fetch entities by group's study year, then map each to a StudentResponse
        return studentRepository.findByGroup_StudyYear(studyYear).stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }


//    @Override
//    public List<Student> findByStudyYear(StudyYear studyYear) {
//        List<Student> students = studentRepository.findByStudyYear(studyYear);
//        return ResponseEntity.ok(students);    }
//
//    @Override
//    public List<Student> search(String name, Long majorId, StudyYear studyYear) {
//        return List.of();
//    }

}
