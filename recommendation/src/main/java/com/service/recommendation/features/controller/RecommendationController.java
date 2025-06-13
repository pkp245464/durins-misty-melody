package com.service.recommendation.features.controller;

import com.service.recommendation.features.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/recommendation-service")
public class RecommendationController {

    private final RecommendationService recommendationService;



}
