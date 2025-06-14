package org.example.attendanceai.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.attendanceai.services.minio.MinioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "File upload", description = "Operations related to file uploading")
public class FileUploadController {

    private final MinioService minioService;

    public FileUploadController(MinioService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/upload")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("objectKey") String objectKey) {
        String fileUrl = minioService.uploadFile(file, objectKey);
        if (fileUrl != null) {
            return ResponseEntity.ok("File uploaded successfully! URL: " + fileUrl);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }

    @GetMapping("/view/{objectKey}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String objectKey) throws IOException {
        ResponseInputStream<GetObjectResponse> fileStreamResponse = minioService.getFileStream(objectKey);
        if (fileStreamResponse != null) {
            try (InputStream fileStream = fileStreamResponse) {
                byte[] fileBytes = fileStream.readAllBytes();
                String contentType = fileStreamResponse.response().contentType(); // Get Content-Type from S3

                if (contentType == null) {
                    // Fallback if content type is not stored or couldn't be determined
                    // You might want to use a more sophisticated content type detection library here
                    contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                }

                return ResponseEntity.ok()
                        // NO Content-Disposition: attachment header to allow inline display
                        .contentType(MediaType.parseMediaType(contentType)) // Set the actual content type
                        .body(fileBytes);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download/{objectKey}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String objectKey) throws IOException {
        InputStream fileStream = minioService.downloadFile(objectKey);
        if (fileStream != null) {
            // Read all bytes from the InputStream
            byte[] fileBytes = fileStream.readAllBytes();
            fileStream.close(); // Close the stream after reading

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + objectKey + "\"")
                    .body(fileBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{objectKey}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteFile(@PathVariable String objectKey) {
        boolean deleted = minioService.deleteFile(objectKey);
        if (deleted) {
            return ResponseEntity.ok("File deleted successfully: " + objectKey);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file: " + objectKey);
        }
    }
}
