package com.service.recommendation.features.service;

import com.service.recommendation.features.dto.AIRecommendationRequest;
import com.service.recommendation.features.dto.AIRecommendationResponse;
import com.service.recommendation.features.dto.MusicDetailDto;
import com.service.recommendation.features.dto.RecommendationResponse;

import java.util.List;

public interface RecommendationService {
    RecommendationResponse getAllRecommendations();
    AIRecommendationResponse getAIRecommendations(AIRecommendationRequest aiRecommendationRequest);
}
