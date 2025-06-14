package org.example.attendanceai.config.minio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class MinioConfig {

    @Value("${MINIO_ACCESS_KEY}")
    private String minioAccessKey;

    @Value("${MINIO_SECRET_KEY}")
    private String minioSecretKey;

    @Value("${MINIO_ENDPOINT}")
    private String minioEndpoint; // This will hold the value from your config/env

    @Bean
    public S3Client s3Client() {
        // Add debug logging
        System.out.println("DEBUG: MinIO Endpoint: '" + minioEndpoint + "'");
        System.out.println("DEBUG: MinIO Access Key: '" + minioAccessKey + "'");

        if (minioEndpoint == null || minioEndpoint.trim().isEmpty()) {
            throw new RuntimeException("MinIO endpoint is not configured properly");
        }

        URI endpointUri;
        try {
            // *** FIX HERE: Use the injected minioEndpoint variable ***
            endpointUri = new URI(minioEndpoint);
        } catch (URISyntaxException e) {
            System.err.println("CRITICAL ERROR: Failed to create MinIO endpoint URI from '" + minioEndpoint + "': " + e.getMessage());
            throw new RuntimeException("MinIO endpoint URI configuration error", e);
        }

        return S3Client.builder()
                .endpointOverride(endpointUri)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(minioAccessKey, minioSecretKey)
                ))
                .region(Region.US_EAST_1)
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build();
    }
}