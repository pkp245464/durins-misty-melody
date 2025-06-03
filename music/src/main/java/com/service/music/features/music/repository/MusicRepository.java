package com.service.music.features.music.repository;

import com.service.music.core.model.MusicModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends MongoRepository<MusicModel, String> {
    @Query("{ $or: [ " +
            "  { 'title':            { $regex: ?0, $options: 'i' } }, " +
            "  { 'tags':             { $regex: ?0, $options: 'i' } }, " +
            "  { 'album.albumName':  { $regex: ?0, $options: 'i' } } " +
            "] }")
    List<MusicModel> searchByKeyword(String keyword);
}
