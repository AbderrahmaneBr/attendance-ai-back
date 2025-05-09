package org.example.attendanceai.services;

import org.example.attendanceai.domain.entity.Major;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
//@Transactional
public interface MajorService {

    List<Major> findAll();

    Optional<Major> findById(long id);

    Major save(Major major);

    Optional<Major> update(long id, Major major);

    boolean deleteById(long id);

    Optional<Major> archive(long id);

    Optional<Major> unarchive(long id);

    Optional<Major> findByDepartmentId(long departmentId);
//    Optional<Major> findByChiefId(long chiefId);}
}