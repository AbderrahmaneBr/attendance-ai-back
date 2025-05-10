package org.example.attendanceai.services.impl;

import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.entity.Teacher;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.enums.UserRoles;
import org.example.attendanceai.domain.repository.TeacherRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
    }

//Todo assk abdo if he has already add this into pom.xml

//    @Autowired
//    private PasswordEncoder passwordEncoder;



    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Optional<User> archive(long id) {
         return userRepository.findById(id).map(user -> {
                user.setArchived(true);
                return userRepository.save(user);
            });
    }

    @Override
    @Transactional
    public Optional<User> unarchive(long id) {
         return userRepository.findById(id).map(user -> {
                user.setArchived(false);
                return userRepository.save(user);
            });
    }

    @Override
    public List<User> findByRole(UserRoles role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        List<User> matchingUsers = new ArrayList<>();
//Todo a tester apres normalement c'est getAll
        List<User> allUsers = findAll();

        for (User user : allUsers) {
            if (role.equals(user.getRole())) {
                matchingUsers.add(user);
            }
        }

        return matchingUsers;
    }


public User findByUsername(String username) {
    if (username == null || username.isEmpty()) {
        return null;
    }

    try {
        return userRepository.findByEmail(username)
                .orElse(null);
    } catch (Exception e) {
        logger.error("Error finding user by username: " + username, e);
        return null;
    }
}

public Optional<User> findByEmail(String email){
    if (email == null || email.isEmpty()) {
        logger.error("Email empty: " + email);
        return null;
    }

    try {
        return userRepository.findByEmail(email);

    } catch (Exception e) {
        logger.error("Error finding user by email: " + email, e);
        return null;
    }
    }

    @Override
    @Transactional
    public Optional<User> promoteToTeacher(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (teacherRepository.existsByUser(user)) {
            return Optional.of(user);
        }

        Teacher teacher = new Teacher();
        teacher.setUser(user);

        teacherRepository.save(teacher);


        userRepository.save(user);

        return Optional.of(user);
    }

    public Optional<User> revokeTeacherRole(Long userId) {
        // Fetch the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Teacher teacher = teacherRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Teacher role not found"));

        teacherRepository.delete(teacher);


        userRepository.save(user);

        return Optional.of(user);
    }





}
