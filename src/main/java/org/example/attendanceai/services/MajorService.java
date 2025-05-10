package org.example.attendanceai.services;

import org.example.attendanceai.api.request.MajorRequest;
import org.example.attendanceai.api.response.MajorResponse;
import org.example.attendanceai.domain.entity.Major;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
//@Transactional
public interface MajorService {

    List<MajorResponse> findAll();

    Optional<MajorResponse> findById(long id);

    MajorResponse save(MajorRequest request);

    Optional<MajorResponse> update(long id, MajorRequest request);

    boolean deleteById(long id);

    Optional<MajorResponse> archive(long id);

    Optional<MajorResponse> unarchive(long id);

    Optional<MajorResponse> findByDepartmentId(long departmentId);
//    Optional<Major> findByChiefId(long chiefId);}
}