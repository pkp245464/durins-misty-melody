package com.service.streaming.features.repository;

import com.service.streaming.core.model.TranscodeAudio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreamingRepository extends MongoRepository<TranscodeAudio, String> {
    Optional<TranscodeAudio> findByMusicId(String musicId);
}
