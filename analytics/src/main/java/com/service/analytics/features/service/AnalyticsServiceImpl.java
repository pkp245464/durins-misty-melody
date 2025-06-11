package com.service.analytics.features.service;

import com.service.analytics.core.exceptions.GlobalDurinAnalyticsServiceException;
import com.service.analytics.core.model.MusicEvent;
import com.service.analytics.features.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Override
    public Boolean recordPlayEvent(String musicId) {
        log.info("AnalyticsServiceImpl::recordPlayEvent called with musicId: {}", musicId);
        if(Objects.isNull(musicId) || musicId.isBlank()) {
            log.error("AnalyticsServiceImpl::recordPlayEvent failed - musicId is null or blank. {}", musicId);
            throw new GlobalDurinAnalyticsServiceException("AnalyticsServiceImpl::recordPlayEvent failed - musicId is null or blank. " + musicId);
        }
        recordMusicPlayStats(musicId);
        log.info("AnalyticsServiceImpl::recordPlayEvent completed successfully for musicId: {}", musicId);
        return Boolean.TRUE;
    }

    private void recordMusicPlayStats(String musicId) {
        log.info("AnalyticsServiceImpl::updateOrCreateMusicEvent - Attempting to find existing event for musicId: {}", musicId);
        MusicEvent musicEvent = analyticsRepository.findByMusicId(musicId)
                .orElse(MusicEvent.builder()
                        .musicId(musicId)
                        .playCount(0)
                        .playTimestamps(new ArrayList<>())
                        .build());

        musicEvent.setPlayCount(musicEvent.getPlayCount() + 1);
        musicEvent.getPlayTimestamps().add(LocalDateTime.now());
        analyticsRepository.save(musicEvent);
    }
}
