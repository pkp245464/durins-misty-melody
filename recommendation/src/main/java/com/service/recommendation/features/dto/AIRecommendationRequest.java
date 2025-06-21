package com.service.recommendation.features.dto;

import lombok.Data;

import java.util.List;

@Data
public class AIRecommendationRequest {
    private String prompt;
    private List<String> musicIds;
}
