package org.example.attendanceai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AttendanceAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceAiApplication.class, args);
    }

}
