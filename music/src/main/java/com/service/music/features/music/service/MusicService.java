package com.service.music.features.music.service;

import com.service.music.features.music.dto.MusicDto;

public interface MusicService {
    MusicDto getMusicDetailsById(String id);
    MusicDto addMusicDetails(MusicDto musicDto);
    MusicDto updateMusicDetails(MusicDto musicDto);

    // Dedicated endpoint to validate user ID existence for inter-service communication
    Boolean validateMusicId(String musicId);
}
