package com.service.streaming.features.utility;

import com.service.streaming.core.config.StreamingUrlConfig;
import com.service.streaming.core.exceptions.GlobalDurinStreamingServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamingExternalServiceConnector {

    private final WebClient webClient;

    public String getOriginalFileUrlFromMusicService(String musicId) {
        log.debug("StreamingExternalServiceConnector::getOriginalFileUrlFromMusicService - Fetching original file URL for musicId: {}", musicId);

        try {
            String originalFileUrl = webClient.get()
                    .uri(StreamingUrlConfig.MUSIC_SERVICE_URL + "/get-file-url/{musicId}", musicId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> {
                        log.error("StreamingExternalServiceConnector::getOriginalFileUrlFromMusicService - Music service returned error status: {}", response.statusCode());
                        return Mono.error(new GlobalDurinStreamingServiceException(
                                "STREAMING-SERVICE: Music service error: " + response.statusCode()
                        ));
                    })
                    .bodyToMono(String.class)
                    .block();

            if (Objects.isNull(originalFileUrl)) {
                log.error("StreamingExternalServiceConnector::getOriginalFileUrlFromMusicService - Null response received for musicId: {}", musicId);
                throw new GlobalDurinStreamingServiceException("STREAMING-SERVICE: Null response");
            }

            log.info("StreamingExternalServiceConnector::getOriginalFileUrlFromMusicService - Successfully retrieved file URL for musicId: {}", musicId);
            return originalFileUrl;
        }
        catch (Exception e) {
            log.error("StreamingExternalServiceConnector::getOriginalFileUrlFromMusicService - Failed to fetch URL for musicId: {} | Error: {}", musicId, e.getMessage());
            throw new GlobalDurinStreamingServiceException("STREAMING-SERVICE: Communication failed: " + e.getMessage());
        }
    }
}
