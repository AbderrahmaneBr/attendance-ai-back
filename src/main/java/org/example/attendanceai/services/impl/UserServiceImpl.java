package org.example.attendanceai.services.impl;

import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.entity.User;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
}
