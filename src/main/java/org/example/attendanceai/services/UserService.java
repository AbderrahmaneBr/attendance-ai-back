package org.example.attendanceai.services;

import org.example.attendanceai.domain.entity.Major;
import org.example.attendanceai.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(long id);

    User save(User user);

    Optional<User> update(long id, User user);

    boolean deleteById(long id);

    Optional<User> archive(long id);

    Optional<User> unarchive(long id);


}
