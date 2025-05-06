package com.service.music.features.service;

import com.service.music.features.dto.MusicDto;

public interface MusicService {
    MusicDto getMusicDetailsById(String id);
    MusicDto addMusicDetails(MusicDto musicDto);
    MusicDto updateMusicDetails(MusicDto musicDto);
}
