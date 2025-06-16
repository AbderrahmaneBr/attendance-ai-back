package org.example.attendanceai.domain.repository;

import org.example.attendanceai.domain.entity.Session;
import org.example.attendanceai.domain.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
        List<Session> findByDateAndStatus(LocalDate date, SessionStatus status);
        List<Session> findByDateAndStartHourBetweenAndStatus(
                LocalDate date, LocalTime startHourLowerBound, LocalTime startHourUpperBound, SessionStatus status);
        boolean existsByScheduleIdAndDateAndStartHourAndEndHour(Long scheduleId, LocalDate date, LocalTime startHour, LocalTime endHour);
}
