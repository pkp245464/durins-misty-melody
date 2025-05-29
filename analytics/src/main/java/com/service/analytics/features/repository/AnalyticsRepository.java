package com.service.analytics.features.repository;

import com.service.analytics.core.model.MusicEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnalyticsRepository extends MongoRepository<MusicEvent, String> {
    Optional<MusicEvent> findByMusicId(String musicId);
}
