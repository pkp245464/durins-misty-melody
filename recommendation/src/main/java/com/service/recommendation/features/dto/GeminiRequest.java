package com.service.recommendation.features.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GeminiRequest {
    private List<Map<String, List<Map<String, String>>>> contents;

    public GeminiRequest(String input) {
        this.contents = List.of(
                Map.of("parts", List.of(Map.of("text", input)))
        );
    }
}
