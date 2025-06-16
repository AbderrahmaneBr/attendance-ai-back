package org.example.attendanceai.jobs;

import org.example.attendanceai.api.request.facedetection.FaceDetectionStudentResponse;
import org.example.attendanceai.api.request.facedetection.LaunchRequest;
import org.example.attendanceai.domain.entity.Session;
import org.example.attendanceai.domain.entity.Student;
import org.example.attendanceai.domain.enums.SessionStatus;
import org.example.attendanceai.domain.repository.SessionRepository;
import org.example.attendanceai.domain.repository.StudentRepository;
import org.example.attendanceai.services.facedetection.FaceDetectionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CameraTriggerJob {

    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository; // Need this to get students by group
    private final FaceDetectionService faceDetectionServiceClient;

    public CameraTriggerJob(SessionRepository sessionRepository,
                            StudentRepository studentRepository,
                            FaceDetectionService faceDetectionServiceClient) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
        this.faceDetectionServiceClient = faceDetectionServiceClient;
    }

    /**
     * This job runs every 30 seconds (or more frequently if needed, e.g., every 10 seconds).
     * It checks for sessions scheduled to start within the next 15 minutes or that are currently active.
     * The `fixedDelay` ensures there's always a pause between the end of one execution and the start of the next.
     */
    @Scheduled(fixedDelay = 30000) // Runs every 30 seconds
    @Transactional // May need to be transactional if you update session status, but be careful with async calls
    public void checkAndTriggerCamera() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Checking for upcoming sessions at " + now);

        // Define the time window for triggering: 15 minutes before startHour until endHour
        // We look for sessions starting in the next 15 minutes (or already started but not ended)
        LocalTime timeLowerBound = now.toLocalTime().minusMinutes(15); // Start triggering 15 mins before
        LocalTime timeUpperBound = now.toLocalTime().plusMinutes(15); // Or some buffer after current time

        LocalDate today = now.toLocalDate();

        // Find sessions for today that meet the time criteria and haven't been launched yet
        // You'll need to add a method to your SessionRepository for this
        List<Session> upcomingSessions = sessionRepository.findByDateAndStartHourBetweenAndStatus(
                today, timeLowerBound, timeUpperBound, SessionStatus.EMPTY); // Assuming EMPTY means not yet launched

        // Refined query for sessions that should be active or are about to start:
        // (session.date = today)
        // AND (session.startHour <= now.plusMinutes(15)) -- Starts soon
        // AND (session.endHour >= now.minusMinutes(15)) -- Hasn't ended 15 mins ago
        // AND session.status = EMPTY -- Only trigger if not already started/active

        // Let's refine the query in Java to be more robust
        List<Session> candidateSessions = sessionRepository.findByDateAndStatus(today, SessionStatus.EMPTY);

        for (Session session : candidateSessions) {
            LocalTime sessionStartTime = session.getStartHour();
            LocalTime sessionEndTime = session.getEndHour();

            // Calculate the actual trigger window for this session:
                // Trigger from (startHour - 15 minutes) up to (endHour + a small buffer if needed)
            LocalTime triggerWindowStart = sessionStartTime.minusMinutes(15);
            LocalTime triggerWindowEnd = sessionEndTime; // Or sessionEndTime.plusMinutes(X) if you want to allow triggers after end for a bit

            LocalDateTime sessionTriggerStartDateTime = LocalDateTime.of(session.getDate(), triggerWindowStart);
            LocalDateTime sessionTriggerEndDateTime = LocalDateTime.of(session.getDate(), triggerWindowEnd);

            // Check if current time falls within the trigger window for this session
            if (now.isAfter(sessionTriggerStartDateTime) && now.isBefore(sessionTriggerEndDateTime)) {
                System.out.println("Found session about to start/active: ID " + session.getId() + " - " + session.getDate() + " " + session.getStartHour());

                // Check if camera_id exists
                if (session.getClassroom().getCamera_id() == null) {
                    System.err.println("  Classroom " + session.getClassroom().getName() + " (ID: " + session.getClassroom().getId() + ") has no camera_id. Skipping trigger for session " + session.getId());
                    continue;
                }

                // Fetch students for the session's group
                List<Student> studentsInGroup = studentRepository.findByGroup(session.getGroup());
                if (studentsInGroup.isEmpty()) {
                    System.out.println("  No students found for group " + session.getGroup().getName() + " (ID: " + session.getGroup().getId() + "). Skipping trigger for session " + session.getId());
                    continue;
                }

                // Map students to FaceDetectionStudentResponse DTOs
                List<FaceDetectionStudentResponse> studentResponses = studentsInGroup.stream()
                        .map(student -> new FaceDetectionStudentResponse(
                                String.valueOf(student.getId()), // Convert Long to String
                                student.getProfile_img()))
                        .collect(Collectors.toList());

                // Prepare the LaunchRequest
                LaunchRequest launchRequest = new LaunchRequest(
                        String.valueOf(session.getClassroom().getCamera_id()), // Convert Long to String
                        studentResponses,
                        String.valueOf(session.getId()), // Convert Long to String
                        300 // Duration in seconds (5 minutes)
                );

                // Send the signal asynchronously using Mono
                faceDetectionServiceClient.sendStartingSignal(launchRequest)
                        .subscribe(
                                response -> {
                                    System.out.println("Signal sent successfully for session " + session.getId() + ". Status: " + response.getStatusCode());
                                    // Optional: Update session status to STARTED or ACTIVE to prevent re-triggering
                                    // session.setStatus(Session.SessionStatus.ACTIVE);
                                    // sessionRepository.save(session); // Save the updated status
                                },
                                error -> {
                                    System.err.println("Error sending signal for session " + session.getId() + ": " + error.getMessage());
                                    // Log the error, maybe attempt retry, or mark session as "failed_launch"
                                }
                        );
            }
        }
        System.out.println("Finished checking for upcoming sessions.");
    }
}