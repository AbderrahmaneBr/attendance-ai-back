package org.example.attendanceai.services.minio;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List; // Added for listObjects, though not explicitly in original snippet

@Service
public class MinioService {

    private final S3Client s3Client;

    @Value("${MINIO_BUCKET_NAME}")
    private String bucketName;

    @Value("${MINIO_ENDPOINT}")
    private String minioEndpoint; // To construct public URLs if needed

    public MinioService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @PostConstruct
    public void init() {
        System.out.println("MinioService initialized with:");
        System.out.println("  Bucket: " + bucketName);
        System.out.println("  Endpoint: " + minioEndpoint);
        // Call the bucket creation logic here to ensure it exists on startup
        createBucketIfNotExists();
    }

    /**
     * Creates the S3 bucket if it does not already exist.
     * This method is called during the service initialization.
     */
    private void createBucketIfNotExists() {
        try {
            // Check if the bucket already exists
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.headBucket(headBucketRequest);
            System.out.println("MinIO Bucket '" + bucketName + "' already exists.");
        } catch (NoSuchBucketException e) {
            // If NoSuchBucketException is thrown, the bucket does not exist, so create it
            System.out.println("MinIO Bucket '" + bucketName + "' does not exist. Creating now...");
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.createBucket(createBucketRequest);
            System.out.println("MinIO Bucket '" + bucketName + "' created successfully.");
        } catch (S3Exception e) {
            // Catch other S3 related errors (e.g., permission issues, connectivity problems)
            System.err.println("Error checking or creating MinIO bucket '" + bucketName + "': " + e.getMessage());
            // It's good practice to re-throw a runtime exception for critical startup failures
            throw new RuntimeException("Failed to initialize MinIO bucket: " + e.getMessage(), e);
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.err.println("An unexpected error occurred during MinIO bucket initialization: " + e.getMessage());
            throw new RuntimeException("Unexpected error during MinIO bucket initialization: " + e.getMessage(), e);
        }
    }


    public String uploadFile(MultipartFile file, String objectKey) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file.");
        }
        if (bucketName == null || bucketName.isEmpty()) {
            throw new IllegalStateException("MinIO bucket name not configured.");
        }

        try (InputStream is = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(file.getContentType()) // Set content type for proper serving
                    .contentLength(file.getSize())       // Set content length
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(is, file.getSize()));

            if (response.sdkHttpResponse().isSuccessful()) {
                System.out.println("File uploaded successfully. ETag: " + response.eTag());
                // Construct a URL to access the object (path-style access)
                return minioEndpoint + "/" + bucketName + "/" + objectKey;
            } else {
                System.err.println("Failed to upload file. Response: " + response.sdkHttpResponse().statusText().orElse("Unknown error"));
                return null;
            }
        } catch (IOException | S3Exception e) {
            System.err.println("Error uploading file to MinIO: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public InputStream downloadFile(String objectKey) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            return s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            System.err.println("Error downloading file from MinIO: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ResponseInputStream<GetObjectResponse> getFileStream(String objectKey) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            return s3Client.getObject(getObjectRequest); // This returns both stream and response
        } catch (S3Exception e) {
            System.err.println("Error getting file stream from MinIO: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteFile(String objectKey) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            System.out.println("File deleted successfully: " + objectKey);
            return true;
        } catch (S3Exception e) {
            System.err.println("Error deleting file from MinIO: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
