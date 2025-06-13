package com.service.analytics.features.service;

import java.util.List;

public interface AnalyticsService {

    List<String> getAllTimeMostPlayedSongs(Integer limit);
    List<String> getTodayMostPlayedSongs(Integer limit);
    List<String> getTrendingSongs(Integer limit);

    /**
     * Records a play event for the given music track.
     * <p>
     * This method is primarily intended for inter-service communication
     * to log when a song is played.
     *
     * @param musicId the unique identifier of the music track
     * @return true if the event was successfully recorded; false otherwise
     */
    Boolean recordPlayEvent(String musicId);
}
