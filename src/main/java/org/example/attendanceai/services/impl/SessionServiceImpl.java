package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.SessionRequest;
import org.example.attendanceai.api.response.SessionResponse;
import org.example.attendanceai.domain.entity.*;
import org.example.attendanceai.domain.mapper.SessionMapper;
import org.example.attendanceai.domain.repository.*;
import org.example.attendanceai.services.SessionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClassroomRepository classroomRepository;

    @Override
    public List<SessionResponse> findAll() {
        return sessionRepository.findAll().stream().map(sessionMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<SessionResponse> findById(long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Session session = sessionRepository.findById(id).orElseThrow(() -> new RuntimeException("Session not found"));
        long sessionOwner = session.getSubject().getTeacher().getId();
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        if(sessionOwner == user.getId() || isAdmin) {
        return Optional.of(sessionMapper.toResponse(sessionRepository.findById(id).orElseThrow(() -> new RuntimeException("Session Not Found"))));
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public SessionResponse save(SessionRequest request) {

        Session session = new Session();

        if (request.getDate() != null) {
            session.setDate(request.getDate());
        }

        if (request.getStartHour() != null) {
            session.setStartHour(request.getStartHour());
        }

        if (request.getEndHour() != null) {
            session.setEndHour(request.getEndHour());
        }

        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found"));
            session.setGroup(group);
        }

        if (request.getClassroomId() != null) {
            Classroom classroom = classroomRepository.findById(request.getClassroomId())
                    .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
            session.setClassroom(classroom);
        }

        if (request.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
            session.setSubject(subject);
        }

        if (request.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                    .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
            session.setSchedule(schedule);
        }

        if (request.getStatus() != null) {
            session.setStatus(request.getStatus());
        }

        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
            session.setGroup(group);
        }

        Session savedSession = sessionRepository.save(session);

        return sessionMapper.toResponse(savedSession);
    }

    @Override
    public Optional<SessionResponse> update(long id, SessionRequest request) {
        /* Allow Teacher (user) to update session only if he's the one who created it  */
        // Find User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find Record
        Session sessionQuery = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Check if user is ADMIN or owner of the record
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        boolean isOwner = sessionQuery.getSubject().getTeacher().getUserId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You don't have permission to update this Session.");
        }

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (request.getDate() != null) {
            session.setDate(request.getDate());
        }

        if (request.getStartHour() != null) {
            session.setStartHour(request.getStartHour());
        }

        if (request.getEndHour() != null) {
            session.setEndHour(request.getEndHour());
        }

        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found"));
            session.setGroup(group);
        }

        if (request.getClassroomId() != null) {
            Classroom classroom = classroomRepository.findById(request.getClassroomId())
                    .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
            session.setClassroom(classroom);
        }

        if (request.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
            session.setSubject(subject);
        }

        if (request.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                    .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
            session.setSchedule(schedule);
        }

        if (request.getStatus() != null) {
            session.setStatus(request.getStatus());
        }

        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
            session.setGroup(group);
        }

        Session savedSession = sessionRepository.save(session);
        return Optional.of(sessionMapper.toResponse(savedSession));

    }

    @Override
    public boolean deleteById(long id) {
        if (sessionRepository.existsById(id)) {
            sessionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<SessionResponse> archive(long id) {
        /* Allow Teacher (user) to update session only if he's the one who created it  */
        // Find User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find Record
        Session sessionQuery = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Check if user is ADMIN or owner of the record
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        boolean isOwner = sessionQuery.getSubject().getTeacher().getUserId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You don't have permission to update this Session.");
        }

        sessionQuery.setArchived(true);
        Session savedSession = sessionRepository.save(sessionQuery);
        return Optional.of(sessionMapper.toResponse(savedSession));
    }

    @Override
    public Optional<SessionResponse> unarchive(long id) {
        /* Allow Teacher (user) to update session only if he's the one who created it  */
        // Find User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find Record
        Session sessionQuery = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Check if user is ADMIN or owner of the record
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        boolean isOwner = sessionQuery.getSubject().getTeacher().getUserId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You don't have permission to update this Session.");
        }

        sessionQuery.setArchived(false);
        Session savedSession = sessionRepository.save(sessionQuery);
        return Optional.of(sessionMapper.toResponse(savedSession));
    }
}
