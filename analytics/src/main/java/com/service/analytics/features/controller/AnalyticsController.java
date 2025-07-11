package com.service.analytics.features.controller;

import com.service.analytics.features.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/analytics-service")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/most-played/all-time")
    public ResponseEntity<List<String>> getAllTimeMostPlayedSongs(@RequestParam(defaultValue = "100") Integer limit) {
        log.info("AnalyticsController::getAllTimeMostPlayedSongs called with limit: {}", limit);
        List<String> result = analyticsService.getAllTimeMostPlayedSongs(limit);
        log.info("AnalyticsController::getAllTimeMostPlayedSongs success with limit: {}", limit);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/most-played/today")
    public ResponseEntity<List<String>> getTodayMostPlayedSongs(@RequestParam(defaultValue = "50") int limit) {
        log.info("AnalyticsController::getTodayMostPlayedSongs called with limit: {}", limit);
        List<String> result = analyticsService.getTodayMostPlayedSongs(limit);
        log.info("AnalyticsController::getTodayMostPlayedSongs success with limit: {}", limit);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<String>> getTrendingSongs(@RequestParam(defaultValue = "20") int limit) {
        log.info("AnalyticsController::getTrendingSongs called with limit: {}", limit);
        List<String> result = analyticsService.getTrendingSongs(limit);
        log.info("AnalyticsController::getTrendingSongs success with limit: {}", limit);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/record/{musicId}")
    ResponseEntity<Boolean> recordPlayEvent(@PathVariable String musicId) {
        log.info("AnalyticsController::recordPlayEvent called with musicId: {}", musicId);
        Boolean result = analyticsService.recordPlayEvent(musicId);
        log.info("AnalyticsController::recordPlayEvent success for musicId: {}", musicId);
        return ResponseEntity.ok(result);
    }
}
