package com.service.recommendation.features.service;

import com.service.recommendation.features.dto.AIRecommendationRequest;
import com.service.recommendation.features.dto.AIRecommendationResponse;
import com.service.recommendation.features.dto.MusicDetailDto;
import com.service.recommendation.features.dto.RecommendationResponse;
import com.service.recommendation.features.utility.RecommendationServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final GeminiService geminiService;
    private final RecommendationServiceClient recommendationServiceClient;

    @Override
    public RecommendationResponse getAllRecommendations() {
        log.info("RecommendationServiceImpl::getAllRecommendations - Compiling comprehensive recommendations");

        // Fetch all data with proper logging
        log.info("RecommendationServiceImpl::getAllRecommendations - Fetching trending songs");
        List<String> trending = safeFetch(() -> recommendationServiceClient.fetchTrendingSongsFromAnalyticsService(20), "trending");
        log.info("RecommendationServiceImpl::getAllRecommendations - Fetched {} trending songs", trending.size());

        log.info("RecommendationServiceImpl::getAllRecommendations - Fetching today's hits");
        List<String> todayHits = safeFetch(() -> recommendationServiceClient.fetchTodayMostPlayedSongsFromAnalyticsService(20), "today's hits");
        log.info("RecommendationServiceImpl::getAllRecommendations - Fetched {} today's hits", todayHits.size());

        log.info("RecommendationServiceImpl::getAllRecommendations - Fetching all-time classics");
        List<String> classics = safeFetch(() -> recommendationServiceClient.fetchAllTimeMostPlayedSongsFromAnalyticsService(20), "classics");
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

    // TODO: AI returned valid-looking IDs, but the recommendedSongs list is empty.

    @Override
    public AIRecommendationResponse getAIRecommendations(AIRecommendationRequest aiRecommendationRequest) {
        log.info("RecommendationServiceImpl::getAIRecommendations - Prompt: {}, Music IDs: {}",
                aiRecommendationRequest.getPrompt(), aiRecommendationRequest.getMusicIds());

        List<MusicDetailDto> inputMusicDetails = Optional.ofNullable(aiRecommendationRequest.getMusicIds())
                .orElse(Collections.emptyList())
                .stream()
                .map(id -> {
                    try {
                        MusicDetailDto dto = recommendationServiceClient.fetchMusicDetailsFromMusicService(id);
                        log.debug("RecommendationServiceImpl::getAIRecommendations - Fetched music detail for ID {}: {}", id, dto);
                        return recommendationServiceClient.fetchMusicDetailsFromMusicService(id);
                    } catch (Exception e) {
                        log.warn("RecommendationServiceImpl::getAIRecommendations - Failed to fetch music detail for ID: {}", id, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        log.info("RecommendationServiceImpl::getAIRecommendations - Fetched {} input music details", inputMusicDetails.size());

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Recommend 10 songs similar to these tracks and user's query. ");
        promptBuilder.append("Return ONLY comma-separated music IDs from the 'Available IDs' list.\n\n");

        promptBuilder.append("### Input Songs:\n");
        for (MusicDetailDto detail : inputMusicDetails) {
            promptBuilder.append(String.format("- ID: %s | Title: %s | Artist: %s | Genre: %s\n",
                    detail.getMusicId(), detail.getTitle(), detail.getArtistName(), detail.getTags()));
        }

        promptBuilder.append("\n### User's Prompt:\n").append(aiRecommendationRequest.getPrompt()).append("\n\n");

        promptBuilder.append("### Available IDs (Sample from catalog):\n");
        List<String> sampleIds = getSampleCatalogIds();  // Fetch 100 IDs from catalog
        promptBuilder.append(String.join(", ", sampleIds));

        promptBuilder.append("\n\nResponse Format: id1,id2,id3,...");

        String finalPrompt = promptBuilder.toString();
        log.debug("RecommendationServiceImpl::getAIRecommendations - Final prompt to AI:\n{}", finalPrompt);

        String aiResponse = geminiService.getResponse(finalPrompt);
        log.info("RecommendationServiceImpl::getAIRecommendations, Raw AI response: {}", aiResponse);

        List<String> recommendedIds = Arrays.stream(aiResponse.split(","))
                .map(String::trim)
                .filter(id -> id.matches("^[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}$")) // UUID pattern
                .distinct()
                .limit(10)
                .toList();

        log.info("RecommendationServiceImpl::getAIRecommendations - Extracted and validated {} recommended IDs: {}", recommendedIds.size(), recommendedIds);

        List<MusicDetailDto> recommendedSongs = recommendedIds.stream()
                .map(id -> {
                    try {
                        MusicDetailDto dto = recommendationServiceClient.fetchMusicDetailsFromMusicService(id);
                        log.debug("RecommendationServiceImpl::getAIRecommendations - Fetched recommended song for ID {}: {}", id, dto);
                        return recommendationServiceClient.fetchMusicDetailsFromMusicService(id);
                    } catch (Exception e) {
                        log.warn("RecommendationServiceImpl::getAIRecommendations - Failed to fetch recommended song detail for ID: {}", id, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        log.info("RecommendationServiceImpl::getAIRecommendations - Successfully fetched {} recommended songs", recommendedSongs.size());

        return AIRecommendationResponse.builder()
                .aiResponse(aiResponse)
                .recommendedSongs(recommendedSongs)
                .build();
    }

    @Override
    public List<MusicDetailDto> testFetchMusicDetails(List<String> musicIds) {
        log.info("RecommendationServiceImpl::testFetchMusicDetails - Fetching music details for IDs: {}", musicIds);

        return musicIds.stream()
                .map(id -> {
                    try {
                        MusicDetailDto dto = recommendationServiceClient.fetchMusicDetailsFromMusicService(id);
                        log.debug("Fetched music detail for ID {}: {}", id, dto);
                        return dto;
                    } catch (Exception e) {
                        log.warn("Failed to fetch music detail for ID: {}", id, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private List<String> getSampleCatalogIds() {
        List<String> ids = new ArrayList<>();
        ids.addAll(safeFetch(() -> recommendationServiceClient.fetchTrendingSongsFromAnalyticsService(40), "trending"));
        ids.addAll(safeFetch(() -> recommendationServiceClient.fetchTodayMostPlayedSongsFromAnalyticsService(40), "todayHits"));
        ids.addAll(safeFetch(() -> recommendationServiceClient.fetchAllTimeMostPlayedSongsFromAnalyticsService(40), "classics"));
        return ids.stream().distinct().limit(100).toList(); // Ensure <=100 IDs
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
