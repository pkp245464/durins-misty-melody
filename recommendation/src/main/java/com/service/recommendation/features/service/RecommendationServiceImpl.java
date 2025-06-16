package com.service.recommendation.features.service;

import com.service.recommendation.features.dto.RecommendationResponse;
import com.service.recommendation.features.utility.RecommendationServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationServiceClient recommendationServiceClient;

    @Override
    public RecommendationResponse getAllRecommendations() {
        log.info("RecommendationServiceImpl::getAllRecommendations - Compiling comprehensive recommendations");

        // Fetch all data with proper logging
        log.info("RecommendationServiceImpl::getAllRecommendations - Fetching trending songs");
        List<String> trending = safeFetch(() -> recommendationServiceClient.getTrendingSongs(20), "trending");
        log.info("RecommendationServiceImpl::getAllRecommendations - Fetched {} trending songs", trending.size());

        log.info("RecommendationServiceImpl::getAllRecommendations - Fetching today's hits");
        List<String> todayHits = safeFetch(() -> recommendationServiceClient.getTodayMostPlayedSongs(20), "today's hits");
        log.info("RecommendationServiceImpl::getAllRecommendations - Fetched {} today's hits", todayHits.size());

        log.info("RecommendationServiceImpl::getAllRecommendations - Fetching all-time classics");
        List<String> classics = safeFetch(() -> recommendationServiceClient.getAllTimeMostPlayedSongs(20), "classics");
        log.info("RecommendationServiceImpl::getAllRecommendations - Fetched {} classics", classics.size());

        // Create fresh picks with logging
        log.info("RecommendationServiceImpl::getAllRecommendations - Creating fresh picks mix");
        List<String> freshPicks = new ArrayList<>();
        freshPicks.addAll(getRandomSubset(trending, 5));
        freshPicks.addAll(getRandomSubset(todayHits, 5));
        freshPicks = freshPicks.stream().distinct().collect(Collectors.toList());
        log.info("RecommendationServiceImpl::getAllRecommendations - Created {} fresh picks", freshPicks.size());

        // Remove overlaps with logging
        log.info("RecommendationServiceImpl::getAllRecommendations - Removing duplicates across categories");
        int beforeTodayHits = todayHits.size();
        int beforeClassics = classics.size();

        todayHits.removeAll(trending);
        classics.removeAll(trending);
        classics.removeAll(todayHits);

        log.info("RecommendationServiceImpl::getAllRecommendations - Removed {} duplicates (todayHits: {}->{}, classics: {}->{})",
                (beforeTodayHits - todayHits.size()) + (beforeClassics - classics.size()),
                beforeTodayHits, todayHits.size(),
                beforeClassics, classics.size());

        // Build response with logging
        log.info("RecommendationServiceImpl::getAllRecommendations - Building final response");
        RecommendationResponse response = RecommendationResponse.builder()
                .trendingNow(limit(trending, 10))
                .todaysHits(limit(todayHits, 10))
                .allTimeFavorites(limit(classics, 10))
                .freshPicks(limit(freshPicks, 8))
                .build();

        log.info("RecommendationServiceImpl::getAllRecommendations - Successfully compiled recommendations. " +
                        "Final counts - Trending: {}, Today: {}, Classics: {}, Fresh: {}",
                response.getTrendingNow().size(),
                response.getTodaysHits().size(),
                response.getAllTimeFavorites().size(),
                response.getFreshPicks().size());

        return response;
    }

    private List<String> safeFetch(DataFetcher fetcher, String category) {
        try {
            log.debug("RecommendationServiceImpl::safeFetch - Attempting to fetch {} songs", category);
            List<String> result = fetcher.fetch();
            log.debug("RecommendationServiceImpl::safeFetch - Successfully fetched {} {} songs", result.size(), category);
            return result;
        } catch (Exception e) {
            log.warn("RecommendationServiceImpl::safeFetch - Failed to fetch {} songs. Error: {}. Returning empty list",
                    category, e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<String> getRandomSubset(List<String> source, int size) {
        log.debug("RecommendationServiceImpl::getRandomSubset - Getting {} random items from list of size {}", size, source.size());
        if (source.size() <= size) {
            log.debug("RecommendationServiceImpl::getRandomSubset - Source size <= requested size, returning full list");
            return new ArrayList<>(source);
        }
        Collections.shuffle(source);
        return source.subList(0, size);
    }

    private List<String> limit(List<String> list, int max) {
        log.debug("RecommendationServiceImpl::limit - Limiting list from {} to {} items", list.size(), max);
        return list.size() > max ? list.subList(0, max) : list;
    }

    @FunctionalInterface
    private interface DataFetcher {
        List<String> fetch();
    }
}
