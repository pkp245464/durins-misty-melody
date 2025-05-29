package com.service.music.features.music.service;

import com.service.music.core.config.MusicUrlConfig;
import com.service.music.core.exceptions.GlobalDurinMusicServiceException;
import com.service.music.core.model.MusicModel;
import com.service.music.features.music.dto.MusicDto;
import com.service.music.features.music.repository.MusicRepository;
import com.service.music.features.music.utility.MusicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;
    private final WebClient webClient;

    @Override
    public MusicDto getMusicDetailsById(String musicId) {
        log.info("MusicServiceImpl::getMusicDetailsById called with input: {}", musicId);
        if(Objects.isNull(musicId) || musicId.isBlank()) {
            log.error("MusicServiceImpl::getMusicDetailsById failed - ID is null or blank.");
            throw new GlobalDurinMusicServiceException("MusicServiceImpl::getMusicDetailsById failed - ID is null or blank.");
        }
        MusicModel musicModel = musicRepository.findById(musicId)
                .orElseThrow(()-> new GlobalDurinMusicServiceException("MusicServiceImpl::getMusicDetailsById failed - Music with ID: " + musicId + " does not exist."));
        recordMusicPlayEvent(musicId);
        return MusicMapper.mapMusicDetailsModelToDto(musicModel);
    }

    public void recordMusicPlayEvent(String musicId) {
        String ANALYTICS_SERVICE_URL = MusicUrlConfig.ANALYTICS_SERVICE_URL;
        Boolean result = webClient.get()
                .uri(ANALYTICS_SERVICE_URL + "/record/{musicId}", musicId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(result)) {
            log.error("MusicServiceImpl::recordPlayAnalytics - Failed to call analytics service for musicId: {}", musicId);
            throw new GlobalDurinMusicServiceException("MusicServiceImpl::recordPlayAnalytics - Failed to call analytics service for musicId: " + musicId);
        }
    }

    @Override
    public MusicDto addMusicDetails(MusicDto musicDto) {
        log.info("MusicServiceImpl::addMusicDetails called with input: {}", musicDto);
        if(Objects.isNull(musicDto)) {
            log.error("MusicServiceImpl::addMusicDetails failed - MusicDto is null.");
            throw new GlobalDurinMusicServiceException("MusicServiceImpl::addMusicDetails failed - MusicDto is null.");
        }
        MusicModel musicModel = MusicMapper.mapMusicDetailsDtoToModel(musicDto);
        log.info("MusicServiceImpl::addMusicDetails returning saved music DTO: {}", musicModel);
        MusicModel savedMusic = musicRepository.save(musicModel);
        log.info("MusicServiceImpl::addMusicDetails success - Music saved with ID: {}", savedMusic.getId());
        return MusicMapper.mapMusicDetailsModelToDto(savedMusic);
    }

    @Override
    public MusicDto updateMusicDetails(MusicDto musicDto) {
        return null;
    }

    @Override
    public Boolean validateMusicId(String musicId) {
        MusicModel musicModel = musicRepository.findById(musicId)
                .orElseThrow(()-> new GlobalDurinMusicServiceException("MusicServiceImpl::getMusicDetailsById failed - Music with ID: " + musicId + " does not exist."));
        return Boolean.TRUE;
    }
}
