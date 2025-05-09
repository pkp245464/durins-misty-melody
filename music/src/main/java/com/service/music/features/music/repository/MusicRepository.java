package com.service.music.features.music.repository;

import com.service.music.core.model.MusicModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends MongoRepository<MusicModel, String> {

}
