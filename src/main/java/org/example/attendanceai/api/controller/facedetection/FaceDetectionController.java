package org.example.attendanceai.api.controller.facedetection;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.attendanceai.api.request.facedetection.LaunchRequest;
import org.example.attendanceai.api.request.facedetection.LaunchResultRequest;
import org.example.attendanceai.services.PresenceService;
import org.example.attendanceai.services.facedetection.FaceDetectionService;
import org.example.attendanceai.services.impl.PresenceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/facedetection")
@Tag(name = "Face Detection", description = "Operations related to face detection")
public class FaceDetectionController {

    private final FaceDetectionService faceDetectionService;
    private final PresenceService presenceService;

    public FaceDetectionController(final FaceDetectionService faceDetectionService, PresenceServiceImpl presenceService) {
        this.faceDetectionService = faceDetectionService;
        this.presenceService = presenceService;
    }

    @PostMapping("/start")
    public Mono<ResponseEntity<String>> detectFaces(@RequestBody LaunchRequest launchRequest) {
        return faceDetectionService.sendStartingSignal(launchRequest);
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Object>> saveDetection(@RequestBody LaunchResultRequest mlResponse) {
        //         ^^^^^^^^^^^ This is the crucial addition!
        System.out.println("Received ML result for session: " + mlResponse.getSession_id() +
                ", status: " + mlResponse.getStatus());
        // For debugging, print the whole object if you want to see all received data
        // System.out.println("Full ML Response: " + mlResponse.toString());

        return presenceService.processMLPresenceResult(mlResponse)
                .thenReturn(ResponseEntity.status(HttpStatus.OK).build()) // Return 200 OK on success
                .onErrorResume(e -> {
                    System.err.println("Error processing ML presence result: " + e.getMessage());
                    // Return an appropriate error response, e.g., 500 Internal Server Error
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

}
