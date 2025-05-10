package org.example.attendanceai.services.impl;

import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.enums.UserRoles;
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

//Todo assk abdo if he has already add this into pom.xml

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(long id, User userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            // Update fields
            if (userDetails.getFirstname() != null) {
                existingUser.setFirstname(userDetails.getFirstname());
            }
            if (userDetails.getLastname() != null) {
                existingUser.setLastname(userDetails.getLastname());
            }
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getProfile_img() != null) {
                existingUser.setProfile_img(userDetails.getProfile_img());
            }
            if (userDetails.getRole() != null) {
                existingUser.setRole(userDetails.getRole());
            }
            if (userDetails.getArchived() != null) {
                existingUser.setArchived(userDetails.getArchived());
            }

            return userRepository.save(existingUser);
        });
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

//    public User findByUsername(String username) {
//        if (username == null) {
//            throw new IllegalArgumentException("Username cannot be null");
//        }
//
//    }
public User findByUsername(String username) {
    if (username == null || username.isEmpty()) {
        return null;
    }

    try {
        return userRepository.findByUsername(username)
                .orElse(null);
    } catch (Exception e) {
        logger.error("Error finding user by username: " + username, e);
        return null;
    }
}

//Todo change this dto into request
    public boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO) {
        // Input validation
        if (username == null || username.isEmpty() || passwordChangeDTO == null) {
            return false;
        }

        // Find the user by username
        User user = findByUsername(username);
        if (user == null) {
            return false; // User not found
        }

        // Verify the current password is correct
        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
            return false; // Current password is incorrect
        }

        // Validate new password (you can add more validation rules)
        if (passwordChangeDTO.getNewPassword() == null ||
                passwordChangeDTO.getNewPassword().isEmpty() ||
                passwordChangeDTO.getNewPassword().length() < 8) {
            return false; // New password doesn't meet requirements
        }

        // Encode the new password and save it
        String encodedPassword = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
        user.setPassword(encodedPassword);

        // Save the updated user
        userRepository.save(user);

        return true;
    }
}
