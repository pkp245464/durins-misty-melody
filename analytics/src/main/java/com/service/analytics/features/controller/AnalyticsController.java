package com.service.analytics.features.controller;

import com.service.analytics.features.service.AnalyticsService;
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
@RequestMapping("/durin's-misty-melody/analytics-service")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/record/{musicId}")
    ResponseEntity<Boolean> recordPlayEvent(@PathVariable String musicId) {
        log.info("AnalyticsController::recordPlayEvent called with musicId: {}", musicId);
        Boolean result = analyticsService.recordPlayEvent(musicId);
        log.info("AnalyticsController::recordPlayEvent success for musicId: {}", musicId);
        return ResponseEntity.ok(result);
    }
}
