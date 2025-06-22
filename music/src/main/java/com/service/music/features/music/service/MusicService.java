package com.service.music.features.music.service;

import com.service.music.features.music.dto.MusicDetailDto;
import com.service.music.features.music.dto.MusicDto;
import com.service.music.features.music.dto.MusicSearchDto;

import java.util.List;

public interface MusicService {
    MusicDto getMusicDetailsById(String id);
    MusicDto addMusicDetails(MusicDto musicDto);
    MusicDto updateMusicDetails(MusicDto musicDto);

    // Dedicated endpoint to validate user ID existence for inter-service communication
    Boolean validateMusicId(String musicId);
    List<MusicSearchDto> searchMusicByKeyword(String keyword);

    //Dedicate for other microservices
    String getMusicFileUrlById(String musicId);
    MusicDetailDto getSimplifiedMusicDetails(String musicId);
}
