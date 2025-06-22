package com.service.recommendation.features.controller;

import com.service.recommendation.features.dto.AIRecommendationRequest;
import com.service.recommendation.features.dto.AIRecommendationResponse;
import com.service.recommendation.features.dto.MusicDetailDto;
import com.service.recommendation.features.dto.RecommendationResponse;
import com.service.recommendation.features.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/recommendation-service")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public ResponseEntity<RecommendationResponse> getAllRecommendations() {
        log.info("RecommendationController::getAllRecommendations - Fetching all recommendation categories");
        RecommendationResponse response = recommendationService.getAllRecommendations();
        log.info("RecommendationController::getAllRecommendations - Successfully compiled recommendations: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ai-recommendations")
    public ResponseEntity<AIRecommendationResponse> getAIRecommendations(@RequestBody AIRecommendationRequest aiRecommendationRequest) {
        log.info("RecommendationController::getAIRecommendations - Received request with prompt: {}, musicIds: {}",
                aiRecommendationRequest.getPrompt(), aiRecommendationRequest.getMusicIds());
        AIRecommendationResponse response = recommendationService.getAIRecommendations(aiRecommendationRequest);
        log.info("RecommendationController::getAIRecommendations - Returning AI recommendations: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test-music-details")
    public ResponseEntity<List<MusicDetailDto>> testFetchMusicDetails(@RequestBody List<String> musicIds) {
        log.info("RecommendationController::testFetchMusicDetails - Received request for music IDs: {}", musicIds);
        List<MusicDetailDto> musicDetails = recommendationService.testFetchMusicDetails(musicIds);
        log.info("RecommendationController::testFetchMusicDetails - Retrieved {} music details", musicDetails.size());
        return ResponseEntity.ok(musicDetails);
    }

}
