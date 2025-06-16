package com.service.recommendation.features.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecommendationResponse {
    private List<String> trendingNow;
    private List<String> todaysHits;
    private List<String> allTimeFavorites;
    private List<String> freshPicks;
}
