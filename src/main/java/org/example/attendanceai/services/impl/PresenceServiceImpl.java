package org.example.attendanceai.services.impl;

import lombok.AllArgsConstructor;
import org.example.attendanceai.api.request.PresenceRequest;
import org.example.attendanceai.api.response.PresenceResponse;
import org.example.attendanceai.api.request.facedetection.LaunchResultRequest;
import org.example.attendanceai.config.security.JwtService;
import org.example.attendanceai.domain.entity.*;
import org.example.attendanceai.domain.enums.PresenceStatus;
import org.example.attendanceai.domain.enums.SessionStatus;
import org.example.attendanceai.domain.mapper.PresenceMapper;
import org.example.attendanceai.domain.repository.PresenceRepository;
import org.example.attendanceai.domain.repository.SessionRepository;
import org.example.attendanceai.domain.repository.StudentRepository;
import org.example.attendanceai.domain.repository.UserRepository;
import org.example.attendanceai.services.PresenceService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PresenceServiceImpl implements PresenceService {

    private final JwtService jwtService;
    private final PresenceRepository presenceRepository;
    private final PresenceMapper presenceMapper;
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
//    private AuthContextService authContextService;

    @Override
    public List<PresenceResponse> findAll() {
        return presenceRepository.findAll().stream().map(presenceMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<PresenceResponse> findById(long id) {
        return Optional.of(presenceMapper.toResponse(presenceRepository.findById(id).orElseThrow(() -> new RuntimeException("Presence Not Found"))));
    }

    @Override
    public PresenceResponse save(PresenceRequest request) {
        Presence presence = presenceMapper.toEntity(request);

        if (request.getStudentId() != null) {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found"));
            presence.setStudent(student);
        }

        if (request.getSessionId() != null) {
            Session session = sessionRepository.findById(request.getSessionId())
                    .orElseThrow(() -> new IllegalArgumentException("Session not found"));
            presence.setSession(session);
        }

        Presence savedPresence = presenceRepository.save(presence);

        return presenceMapper.toResponse(savedPresence);
    }

    @Override
    public Optional<PresenceResponse> update(long id, PresenceRequest request) {
        /* Allow Teacher (user) to update presence sheets only if he's the one who created them  */
        // Find User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find Record
        Presence presenceQuery = presenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence not found"));

        // Check if user is ADMIN or owner of the record
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        boolean isOwner = presenceQuery.getSession().getSubject().getTeacher().getUserId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You don't have permission to update this Presence.");
        }

        Presence presence = presenceMapper.toEntity(request);
        return presenceRepository.findById(id).map(existingPresence -> {
            // Update basic fields
            if (presence.getDescription() != null) {
                existingPresence.setDescription(presence.getDescription());
            }

            if (presence.getStudent() != null) {
                Student student = studentRepository.findById(presence.getStudent().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Student not found"));
                existingPresence.setStudent(student);
            }

            if (presence.getSession() != null) {
                Session session = sessionRepository.findById(presence.getSession().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Session not found"));
                existingPresence.setSession(session);
            }

            Presence savedPresence = presenceRepository.save(existingPresence);
            PresenceResponse response = presenceMapper.toResponse(savedPresence);

            return response;
        });
    }

    @Override
    public void markPresence(long id, PresenceStatus status) {
        // Find User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find Record
        Presence presenceQuery = presenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence not found"));

        // Check if user is ADMIN or owner of the record
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        boolean isOwner = presenceQuery.getSession().getSubject().getTeacher().getUserId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You don't have permission to update this Presence.");
        }

        presenceRepository.findById(id).stream().forEach(existingPresence -> {
            // Update basic fields
            existingPresence.setStatus(status);
        });

    }

    @Override
    public boolean deleteById(long id) {
        if (presenceRepository.existsById(id)) {
            presenceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PresenceResponse> archive(long id) {
        /* Allow Teacher (user) to update presence sheets only if he's the one who created them  */
        // Find User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find Record
        Presence presenceQuery = presenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence not found"));

        // Check if user is ADMIN or owner of the record
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        boolean isOwner = presenceQuery.getSession().getSubject().getTeacher().getUserId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You don't have permission to update this Presence.");
        }
        presenceQuery.setArchived(true);
        Presence savedPresence = presenceRepository.save(presenceQuery);
        return Optional.of(presenceMapper.toResponse(savedPresence));
    }

    @Override
    public Optional<PresenceResponse> unarchive(long id) {
        /* Allow Teacher (user) to update presence sheets only if he's the one who created them  */
        // Find User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find Record
        Presence presenceQuery = presenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence not found"));

        // Check if user is ADMIN or owner of the record
        boolean isAdmin = user.getRole().name().equals("ADMIN");
        boolean isOwner = presenceQuery.getSession().getSubject().getTeacher().getUserId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You don't have permission to update this Presence.");
        }
        presenceQuery.setArchived(false);
        Presence savedPresence = presenceRepository.save(presenceQuery);
        return Optional.of(presenceMapper.toResponse(savedPresence));
    }

    @Transactional
    @Override
    public Mono<Void> processMLPresenceResult(LaunchResultRequest mlResponse) {
        // 1. Find the target Session
        return Mono.fromCallable(() -> sessionRepository.findById(Long.parseLong(mlResponse.getSession_id())))
                .flatMap(sessionOptional -> {
                    if (sessionOptional.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("Session with ID " + mlResponse.getSession_id() + " not found. Cannot process presence."));
                    }
                    Session session = sessionOptional.get();

                    // 2. Process each student's presence from the ML response
                    List<Mono<Presence>> presenceSaveMonos = mlResponse.getOutput().stream()
                            .map((mlPresenceDTO) -> {
                                // Find student by name (assuming name is unique or you have a strategy for duplicates)
                                return Mono.fromCallable(() -> studentRepository.findById(Long.parseLong(mlPresenceDTO.getStudent_id())))
                                        .flatMap(studentOptional -> {
                                            if (studentOptional.isEmpty()) {
                                                System.err.println("Student with ID " + mlPresenceDTO.getStudent_id() + " not found. Skipping presence record.");
                                                return Mono.empty(); // Skip this student if not found
                                            }
                                            Student student = studentOptional.get();

                                            // Create new Presence entity
                                            Presence presence = Presence.builder()
                                                    .session(session)
                                                    .student(student)
                                                    .status(mlPresenceDTO.getPresent() ? PresenceStatus.PRESENT : PresenceStatus.ABSENT)
                                                    .build();
                                            // Save the Presence entity
                                            return Mono.fromCallable(() -> presenceRepository.save(presence));
                                        })
                                        .onErrorResume(e -> {
                                            System.err.println("Error processing presence for student " + mlPresenceDTO.getStudent_id() + ": " + e.getMessage());
                                            return Mono.empty(); // Continue processing other students even if one fails
                                        });
                            })
                            .toList();

                    // 3. Wait for all presence saves to complete, then update session status
                    return Mono.when(presenceSaveMonos) // Combine all Mono<Presence> into a single Mono<Void>
                            .then(Mono.fromRunnable(() -> {
                                session.setStatus(SessionStatus.COMPLETED);
                                sessionRepository.save(session); // Save updated session status
                                System.out.println("Session " + mlResponse.getSession_id() + " status updated to COMPLETED.");
                            }));
                })
                .doOnError(e -> System.err.println("Failed to process ML presence result for session " + mlResponse.getSession_id() + ": " + e.getMessage()))
                .then(); // Return Mono<Void>
    }
}
