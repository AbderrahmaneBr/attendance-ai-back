package org.example.attendanceai.jobs;

import org.example.attendanceai.domain.entity.*;
import org.example.attendanceai.domain.enums.SessionStatus;
import org.example.attendanceai.domain.model.ScheduleDetails;
import org.example.attendanceai.domain.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class SessionGenerationJob {

    private final ScheduleRepository scheduleRepository;
    private final SessionRepository sessionRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    private final ClassroomRepository classroomRepository;

    public SessionGenerationJob(
            ScheduleRepository scheduleRepository,
            SessionRepository sessionRepository,
            SubjectRepository subjectRepository,
            GroupRepository groupRepository,
            ClassroomRepository classroomRepository) {
        this.scheduleRepository = scheduleRepository;
        this.sessionRepository = sessionRepository;
        this.subjectRepository = subjectRepository;
        this.groupRepository = groupRepository;
        this.classroomRepository = classroomRepository;
    }

    /**
     * This method will run every week, on Monday at midnight (00:00).
     * The sessions generated will be for the *upcoming* week,
     * starting from the Monday of the current job execution.
     *
     * Cron expression: "0 0 0 ? * MON"
     * - 0: at the 0th minute
     * - 0: at the 0th hour (midnight)
     * - 0: on the 0th day of the month (ignored by '?')
     * - ?: no specific day of month (used with specific day of week)
     * - *: every month
     * - MON: on Monday
     */

//    @Scheduled(cron = "0 0 0 ? * MON")
        @Scheduled(cron = "0 * * * * ?")
    @Transactional // Ensures atomicity for the batch of session creations
    public void generateWeeklySessions() {
        System.out.println("Starting weekly session generation job at " + LocalTime.now());

        // Determine the start of the week for which sessions are to be generated.
        // If the job runs on Monday (e.g., June 16, 2025, which is a Monday),
        // we want sessions for June 16-22.
        LocalDate startOfTargetWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfTargetWeek = startOfTargetWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        System.out.println("Generating sessions for the week of " + startOfTargetWeek + " to " + endOfTargetWeek);

        List<Schedule> schedules = scheduleRepository.findAll();

        for (Schedule schedule : schedules) {
            System.out.println("Processing schedule: '" + schedule.getName() + "' (ID: " + schedule.getId() + ", Group: " + schedule.getGroup().getName() + ")");

            if (schedule.getArchived()) {
                System.out.println("  Skipping archived schedule.");
                continue;
            }

            try {
                ScheduleDetails details = schedule.getDetails();

                if (details == null || details.getDays() == null || details.getDays().isEmpty()) {
                    System.out.println("  No details found for schedule ID: " + schedule.getId());
                    continue;
                }

                // Fetch the Group entity once per schedule
                Group group = schedule.getGroup();
                if (group == null) {
                    System.err.println("  Error: Schedule ID " + schedule.getId() + " has no associated group.");
                    continue;
                }

                for (Map.Entry<String, List<ScheduleDetails.SessionSlot>> dayEntry : details.getDays().entrySet()) {
                    String dayName = dayEntry.getKey();
                    List<ScheduleDetails.SessionSlot> sessionSlots = dayEntry.getValue();

                    DayOfWeek scheduleDayOfWeek = parseDayOfWeek(dayName);
                    if (scheduleDayOfWeek == null) {
                        System.err.println("  Warning: Unrecognized day name in schedule details: '" + dayName + "' for schedule ID: " + schedule.getId());
                        continue;
                    }

                    // Calculate the specific date for this day of the week within the target week
                    LocalDate sessionDate = startOfTargetWeek.with(TemporalAdjusters.nextOrSame(scheduleDayOfWeek));

                    // Sanity check: Ensure the session date is within the desired generation window (optional but good)
                    if (sessionDate.isBefore(startOfTargetWeek) || sessionDate.isAfter(endOfTargetWeek)) {
                        System.err.println("  Warning: Calculated session date " + sessionDate + " falls outside the target week for " + dayName + ". Skipping.");
                        continue;
                    }

                    for (ScheduleDetails.SessionSlot slot : sessionSlots) {
                        // Fetch related entities
                        Optional<Subject> subjectOpt = subjectRepository.findById(slot.getSubjectId());
                        Optional<Classroom> classroomOpt = classroomRepository.findById(slot.getClassroomId());

                        if (subjectOpt.isEmpty()) {
                            System.err.println("  Error: Subject with ID " + slot.getSubjectId() + " not found for slot in schedule " + schedule.getId() + ". Skipping session.");
                            continue;
                        }
                        if (classroomOpt.isEmpty()) {
                            System.err.println("  Error: Classroom with ID " + slot.getClassroomId() + " not found for slot in schedule " + schedule.getId() + ". Skipping session.");
                            continue;
                        }

                        Subject subject = subjectOpt.get();
                        Classroom classroom = classroomOpt.get();

                        // Build and save the new Session
                        Session newSession = Session.builder()
                                .date(sessionDate)
                                .startHour(slot.getStartHour())
                                .endHour(slot.getEndHour())
                                .subject(subject)
                                .group(group) // Use the fetched group entity
                                .classroom(classroom)
                                .schedule(schedule)
                                .status(SessionStatus.EMPTY) // Default status
                                .build();

                        // Prevent duplicate sessions for the exact same schedule entry, date, and time
                        // Using the schedule entity's ID for uniqueness check
                        if (!sessionRepository.existsByScheduleIdAndDateAndStartHourAndEndHour(
                                schedule.getId(), newSession.getDate(),
                                newSession.getStartHour(), newSession.getEndHour())) {
                            sessionRepository.save(newSession);
                            System.out.println("  Created session for " + sessionDate + " " + slot.getStartHour() + "-" + slot.getEndHour() +
                                    " (Sub: " + subject.getName() + ", Room: " + classroom.getName() + ")");
                        } else {
                            System.out.println("  Skipped duplicate session for " + sessionDate + " " + slot.getStartHour() + "-" + slot.getEndHour() +
                                    " (Schedule ID: " + schedule.getId() + ")");
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error processing schedule ID " + schedule.getId() + ": " + e.getMessage());
                e.printStackTrace();
                // Depending on your error handling policy, you might re-throw or log and continue
            }
        }
        System.out.println("Finished weekly session generation job.");
    }

    private DayOfWeek parseDayOfWeek(String dayName) {
        try {
            // Use ENGLISH locale to ensure consistent parsing of day names
            return DayOfWeek.valueOf(dayName.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            return null; // Return null if day name is not recognized
        }
    }
}