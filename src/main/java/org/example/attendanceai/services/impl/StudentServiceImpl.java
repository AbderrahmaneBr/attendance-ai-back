package org.example.attendanceai.services.impl;

import org.example.attendanceai.domain.entity.Group;
import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.enums.StudyYear;
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

//Todo ajout anotation to methods (GETMAPPING)
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final MajorRepository majorRepository;


    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository, MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.majorRepository = majorRepository;
    }

        @Override
        @Transactional(readOnly = true)
        public List<Student> findAll() {
            return studentRepository.findAll();
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<Student> findById(long id) {
            return studentRepository.findById(id);
        }

        @Override
        public Student save(Student student) {
            return studentRepository.save(student);
        }

        @Override
        @Transactional
        public Optional<Student> update(long id, Student studentDetails) {
            return studentRepository.findById(id).map(existingStudent -> {
                // Update fields
                if (studentDetails.getFirstname() != null) {
                    existingStudent.setFirstname(studentDetails.getFirstname());
                }
                if (studentDetails.getLastname() != null) {
                    existingStudent.setLastname(studentDetails.getLastname());
                }
                if (studentDetails.getEmail() != null) {
                    existingStudent.setEmail(studentDetails.getEmail());
                }
                if (studentDetails.getProfile_img() != null) {
                    existingStudent.setProfile_img(studentDetails.getProfile_img());
                }
                if (studentDetails.getPhone() != null) {
                    existingStudent.setPhone(studentDetails.getPhone());
                }
                if (studentDetails.getAddress() != null) {
                    existingStudent.setAddress(studentDetails.getAddress());
                }
                existingStudent.setCneId(studentDetails.getCneId());

                if (studentDetails.getGroup() != null) {
                    Group group = groupRepository.findById(studentDetails.getGroup().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Group not found"));
                    existingStudent.setGroup(group);
                }

                if (studentDetails.getMajor() != null) {
                    Major major = majorRepository.findById(studentDetails.getMajor().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Major not found"));
                    existingStudent.setMajor(major);
                }

                return studentRepository.save(existingStudent);
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
        public Optional<Student> archive(long id) {
            return studentRepository.findById(id).map(student -> {
                student.setArchived(true);
                return studentRepository.save(student);
            });
        }

        @Override
        @Transactional
        public Optional<Student> unarchive(long id) {
            return studentRepository.findById(id).map(student -> {
                student.setArchived(false);
                return studentRepository.save(student);
            });
        }

    @Override
    public List<Student> findByMajorId(@PathVariable long majorId) {
        return studentRepository.findByMajorId(majorId);
    }

    @Override
    @GetMapping("/students/by-cne-id/{cne_id}")
    public Optional<Student> findByCneId(@PathVariable("cne_id") long cneId) {
        return studentRepository.findByCneId(cneId);
    }

    @Override
    public List<Student> findByStudyYear(StudyYear studyYear) {
        return studentRepository.findByGroup_StudyYear(studyYear);
    }

    @Override
    public List<Student> findByGroupId(StudyYear studyYear) {
        return studentRepository.findByGroup_StudyYear(studyYear);
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
