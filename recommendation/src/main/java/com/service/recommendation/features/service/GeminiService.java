package com.service.recommendation.features.service;

import com.service.recommendation.features.dto.GeminiRequest;
import com.service.recommendation.features.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public GeminiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getResponse(String input) {
        GeminiRequest request = new GeminiRequest(input);

        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .map(response -> {
                    if (response.getCandidates() == null || response.getCandidates().isEmpty()) {
                        return "No recommendations available";
                    }
                    return response.getCandidates().get(0).getContent().getParts().get(0).getText();
                })
                .onErrorResume(e -> Mono.just("AI service is currently unavailable"))
                .block();
    }
}
