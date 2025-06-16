package com.service.recommendation.features.controller;

import com.service.recommendation.features.dto.RecommendationResponse;
import com.service.recommendation.features.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/recommendation-service")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public ResponseEntity<RecommendationResponse> getAllRecommendations() {
        log.info("Fetching all recommendation categories");
        RecommendationResponse response = recommendationService.getAllRecommendations();
        log.info("Successfully compiled recommendations");
        return ResponseEntity.ok(response);
    }

}
