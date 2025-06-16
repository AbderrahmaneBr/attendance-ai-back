package org.example.attendanceai.api.controller.facedetection;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.attendanceai.api.request.facedetection.LaunchRequest;
import org.example.attendanceai.api.request.facedetection.LaunchResultRequest;
import org.example.attendanceai.services.PresenceService;
import org.example.attendanceai.services.facedetection.FaceDetectionService;
import org.example.attendanceai.services.impl.PresenceServiceImpl;
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
    public Mono<Void> saveDetection(@RequestBody LaunchResultRequest launchResultRequest) {
        return presenceService.processMLPresenceResult(launchResultRequest);
    }

}
