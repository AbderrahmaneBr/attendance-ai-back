package org.example.attendanceai.services.webflux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

@Service
public class ApiClientWebClientService {

    private final WebClient webClient;
    private final String apiKey; // To hold the API key

    public ApiClientWebClientService(WebClient.Builder webClientBuilder,
                                     @Value("${FACEDETECTION_API_URI}") String uri,
                                     @Value("${FACEDETECTION_API_KEY}") String apiKey) { // Inject API key
        this.webClient = webClientBuilder.baseUrl(uri)
                .build();
        this.apiKey = apiKey; // Store the injected API key
    }

    // Example GET request
    public Mono<String> makeGetRequest(String path) {
        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Example POST request with a DTO and API key
    public <T, R> Mono<R> makePostRequest(String path, T requestBody, Class<R> responseType) {
        return webClient.post()
                .uri(path)
                .header("api-key", apiKey) // Add the API key header
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType);
    }

    // You can also get the raw response
    public Mono<ResponseEntity<String>> makeGetRequestWithResponse(String path) {
        return webClient.get()
                .uri(path)
                .retrieve()
                .toEntity(String.class);
    }

    // POST request with a DTO and raw response, including API key
    public <T, R> Mono<ResponseEntity<R>> makePostRequestWithResponse(String path, T requestBody, Class<R> responseType) {
        return webClient.post()
                .uri(path)
                .header("api-key", apiKey) // Add the API key header
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .toEntity(responseType);
    }
}