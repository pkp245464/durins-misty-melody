package com.service.recommendation.features.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIRecommendationResponse {
    private String aiResponse;
    private List<MusicDetailDto> recommendedSongs;
}
