package com.service.streaming.features.service;

import com.service.streaming.core.exceptions.GlobalDurinStreamingServiceException;
import com.service.streaming.core.model.TranscodeAudio;
import com.service.streaming.features.repository.StreamingRepository;
import com.service.streaming.features.utility.StreamingExternalServiceConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamingServiceImpl implements StreamingService{

    private final StreamingRepository streamingRepository;
    private final StreamingExternalServiceConnector streamingExternalServiceConnector;

    @Override
    public String fetchAndStoreOriginalFileUrl(String musicId) {
        log.debug("StreamingServiceImpl::fetchAndStoreOriginalFileUrl - Received request for musicId: {}", musicId);

        if (Objects.isNull(musicId) || musicId.isBlank()) {
            String message = "StreamingServiceImpl::fetchAndStoreOriginalFileUrl - Invalid musicId: " + musicId;
            log.error(message);
            throw new GlobalDurinStreamingServiceException(message);
        }

        Optional<TranscodeAudio> existingRecord = streamingRepository.findByMusicId(musicId);
        if (existingRecord.isPresent()) {
            log.info("StreamingServiceImpl::fetchAndStoreOriginalFileUrl - Existing record found for musicId: {}", musicId);
            return existingRecord.get().getOriginalFileUrl();
        }

        log.debug("StreamingServiceImpl::fetchAndStoreOriginalFileUrl - No record found. Creating new entry.");
        return storeNewOriginalFileRecord(musicId);
    }

    private String storeNewOriginalFileRecord(String musicId) {
        log.debug("StreamingServiceImpl::storeNewOriginalFileRecord - Fetching original file URL for musicId: {}", musicId);

        String originalFileUrl = streamingExternalServiceConnector.getOriginalFileUrlFromMusicService(musicId);

        TranscodeAudio newRecord = TranscodeAudio.builder()
                .musicId(musicId)
                .originalFileUrl(originalFileUrl)
                .originalReceivedAt(LocalDateTime.now())
                .isTranscoded(false)
                .build();

        streamingRepository.save(newRecord);
        log.info("StreamingServiceImpl::storeNewOriginalFileRecord - New record saved for musicId: {}", musicId);

        return originalFileUrl;
    }
}
