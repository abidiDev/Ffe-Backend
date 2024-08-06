package com.pfe.prescription_microservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import reactor.util.retry.Retry;


import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final WebClient webClient;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    public OpenAIServiceImpl(WebClient.Builder webClientBuilder, @Value("${huggingface.api.key}") String apiKey, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("https://api-inference.huggingface.co/models/EleutherAI/gpt-neo-2.7B").build();
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<String> getExplanation(String prompt) {
        HuggingFaceRequest request = new HuggingFaceRequest(prompt);
        System.out.println("Sending request to Hugging Face API: " + request); // Debug statement

        return this.webClient.post()
                .uri("")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::mapResponse)
                .retryWhen(Retry.backoff(5, Duration.ofSeconds(5))
                        .maxBackoff(Duration.ofMinutes(1))
                        .filter(throwable -> throwable instanceof WebClientResponseException &&
                                ((WebClientResponseException) throwable).getStatusCode().value() == 429))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode().value() == 400) {
                        String errorBody = ex.getResponseBodyAsString();
                        System.err.println("Bad Request: " + errorBody); // Log the response body for debugging
                        if (errorBody.contains("token seems invalid")) {
                            return Mono.error(new RuntimeException("Invalid API token. Please check your API key."));
                        }
                        return Mono.error(new RuntimeException("Bad Request: " + errorBody));
                    } else if (ex.getStatusCode().value() == 429) {
                        return Mono.just("Too many requests. Please try again later.");
                    }
                    return Mono.error(ex);
                });
    }

    private String mapResponse(String responseBody) {
        try {
            System.out.println("Raw response: " + responseBody); // Debug statement to log the raw response
            List<Map<String, Object>> responseList = objectMapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>() {});
            if (responseList == null || responseList.isEmpty()) {
                throw new RuntimeException("Received empty response from Hugging Face API");
            }
            Map<String, Object> responseMap = responseList.get(0);
            if (!responseMap.containsKey("generated_text")) {
                throw new RuntimeException("Received response with no generated_text from Hugging Face API");
            }
            String generatedText = (String) responseMap.get("generated_text");
            return generatedText.trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse response from Hugging Face API", e);
        }
    }

    @Data
    private static class HuggingFaceRequest {
        private final String inputs;

        public HuggingFaceRequest(String prompt) {
            this.inputs = prompt;
        }

        @Override
        public String toString() {
            return "HuggingFaceRequest{" +
                    "inputs='" + inputs + '\'' +
                    '}';
        }
    }
}
