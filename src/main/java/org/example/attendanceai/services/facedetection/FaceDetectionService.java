package org.example.attendanceai.services.facedetection;

import org.example.attendanceai.api.request.facedetection.LaunchRequest;
import org.example.attendanceai.api.request.facedetection.LaunchResultRequest;
import org.example.attendanceai.domain.entity.Session;
import org.example.attendanceai.domain.enums.SessionStatus;
import org.example.attendanceai.domain.repository.SessionRepository;
import org.example.attendanceai.services.webflux.ApiClientWebClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FaceDetectionService {
    private final ApiClientWebClientService apiClientWebClientService;
    private final SessionRepository sessionRepository;

    @Value("${FACEDETECTION_API_LAUNCH_ROUTE}")
    String route;


    public FaceDetectionService(ApiClientWebClientService apiClientWebClientService, SessionRepository sessionRepository) {
        this.apiClientWebClientService = apiClientWebClientService;
        this.sessionRepository = sessionRepository;
    }

    public Mono<ResponseEntity<String>> sendStartingSignal(LaunchRequest input) {
        LaunchRequest detectionRequest = new LaunchRequest(
                input.getCamera_id(),
                input.getStudents(),
                input.getSession_id(),
                input.getDuration()
        );


        return apiClientWebClientService.makePostRequestWithResponse(route, detectionRequest, LaunchResultRequest.class)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(responseEntity -> {
                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                        // HTTP 200 (or other 2xx) - proceed
                        String successMessage = "Detection request sent, Session with ID: " + input.getSession_id() + " status is being processed.";

                        // Change Session Status to "pending"
                        Session session = sessionRepository.findById(Long.parseLong(input.getSession_id())).orElseThrow(()->new RuntimeException("Session with id "));
                        session.setStatus(SessionStatus.PENDING);
                        sessionRepository.save(session);

                        return Mono.just(ResponseEntity.ok(successMessage));
                    } else {
                        // Non-2xx status - abort
                        String errorMessage = "External API returned status: " + responseEntity.getStatusCode();
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorMessage));
                    }
                })
                .onErrorResume(e -> {
                    System.err.println("Error sending detection request: " + e.getMessage());
                    String errorMessage = "Failed to send detection request: " + e.getMessage();
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(errorMessage));
                });
    }


}
