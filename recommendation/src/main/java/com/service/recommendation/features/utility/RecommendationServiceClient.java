package com.service.recommendation.features.utility;

import com.service.recommendation.core.config.RecommendationUrlConfig;
import com.service.recommendation.core.exceptions.GlobalDurinRecommendationServiceException;
import com.service.recommendation.features.dto.MusicDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationServiceClient {

    private final WebClient webClient;

    public List<String> fetchAllTimeMostPlayedSongsFromAnalyticsService(int limit) {
        log.info("RecommendationServiceClient::fetchAllTimeMostPlayedSongsFromAnalyticsService called with limit: {}", limit);
        List<String> result = webClient.get()
                .uri(RecommendationUrlConfig.ANALYTICS_SERVICE_URL + "/most-played/all-time?limit=" + limit)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .onErrorResume(e -> {
                    log.error("RecommendationServiceClient::fetchAllTimeMostPlayedSongsFromAnalyticsService failed - Error: {}", e.getMessage());
                    return Mono.error(new GlobalDurinRecommendationServiceException("Failed to get all time most played songs"));
                })
                .block();

        log.info("RecommendationServiceClient::fetchAllTimeMostPlayedSongsFromAnalyticsService success with limit: {}", limit);
        return result;
    }

    public List<String> fetchTodayMostPlayedSongsFromAnalyticsService(int limit) {
        log.info("RecommendationServiceClient::fetchTodayMostPlayedSongsFromAnalyticsService called with limit: {}", limit);
        List<String> result = webClient.get()
                .uri(RecommendationUrlConfig.ANALYTICS_SERVICE_URL + "/most-played/today?limit=" + limit)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .onErrorResume(e -> {
                    log.error("RecommendationServiceClient::fetchTodayMostPlayedSongsFromAnalyticsService failed - Error: {}", e.getMessage());
                    return Mono.error(new GlobalDurinRecommendationServiceException("Failed to get today's most played songs"));
                })
                .block();

        log.info("RecommendationServiceClient::fetchTodayMostPlayedSongsFromAnalyticsService success with limit: {}", limit);
        return result;
    }

    public List<String> fetchTrendingSongsFromAnalyticsService(int limit) {
        log.info("RecommendationServiceClient::fetchTrendingSongsFromAnalyticsService called with limit: {}", limit);
        List<String> result = webClient.get()
                .uri(RecommendationUrlConfig.ANALYTICS_SERVICE_URL + "/trending?limit=" + limit)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .onErrorResume(e -> {
                    log.error("RecommendationServiceClient::fetchTrendingSongsFromAnalyticsService failed - Error: {}", e.getMessage());
                    return Mono.error(new GlobalDurinRecommendationServiceException("Failed to get trending songs"));
                })
                .block();

        log.info("RecommendationServiceClient::fetchTrendingSongsFromAnalyticsService success with limit: {}", limit);
        return result;
    }

    public MusicDetailDto fetchMusicDetailsFromMusicService(String musicId) {
        log.info("RecommendationServiceClient::Fetching music details for ID: {}", musicId);
        return webClient.get()
                .uri(RecommendationUrlConfig.MUSIC_SERVICE_URL + "/get-music-details/{id}", musicId)
                .retrieve()
                .bodyToMono(MusicDetailDto.class)
                .block();
    }
}
