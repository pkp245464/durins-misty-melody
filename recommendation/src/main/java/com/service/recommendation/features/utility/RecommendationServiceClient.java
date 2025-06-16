package com.service.recommendation.features.utility;

import com.service.recommendation.core.config.RecommendationUrlConfig;
import com.service.recommendation.core.exceptions.GlobalDurinRecommendationServiceException;
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

    public List<String> getAllTimeMostPlayedSongs(int limit) {
        log.info("RecommendationServiceClient::getAllTimeMostPlayedSongs called with limit: {}", limit);
        List<String> result = webClient.get()
                .uri(RecommendationUrlConfig.ANALYTICS_SERVICE_URL + "/most-played/all-time?limit=" + limit)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .onErrorResume(e -> {
                    log.error("RecommendationServiceClient::getAllTimeMostPlayedSongs failed - Error: {}", e.getMessage());
                    return Mono.error(new GlobalDurinRecommendationServiceException("Failed to get all time most played songs"));
                })
                .block();

        log.info("RecommendationServiceClient::getAllTimeMostPlayedSongs success with limit: {}", limit);
        return result;
    }

    public List<String> getTodayMostPlayedSongs(int limit) {
        log.info("RecommendationServiceClient::getTodayMostPlayedSongs called with limit: {}", limit);
        List<String> result = webClient.get()
                .uri(RecommendationUrlConfig.ANALYTICS_SERVICE_URL + "/most-played/today?limit=" + limit)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .onErrorResume(e -> {
                    log.error("RecommendationServiceClient::getTodayMostPlayedSongs failed - Error: {}", e.getMessage());
                    return Mono.error(new GlobalDurinRecommendationServiceException("Failed to get today's most played songs"));
                })
                .block();

        log.info("RecommendationServiceClient::getTodayMostPlayedSongs success with limit: {}", limit);
        return result;
    }

    public List<String> getTrendingSongs(int limit) {
        log.info("RecommendationServiceClient::getTrendingSongs called with limit: {}", limit);
        List<String> result = webClient.get()
                .uri(RecommendationUrlConfig.ANALYTICS_SERVICE_URL + "/trending?limit=" + limit)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .onErrorResume(e -> {
                    log.error("RecommendationServiceClient::getTrendingSongs failed - Error: {}", e.getMessage());
                    return Mono.error(new GlobalDurinRecommendationServiceException("Failed to get trending songs"));
                })
                .block();

        log.info("RecommendationServiceClient::getTrendingSongs success with limit: {}", limit);
        return result;
    }
}
