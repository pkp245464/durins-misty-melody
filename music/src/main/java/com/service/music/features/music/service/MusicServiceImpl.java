package com.service.music.features.music.service;

import com.service.music.core.exceptions.GlobalDurinMusicServiceException;
import com.service.music.core.model.MusicModel;
import com.service.music.features.music.dto.MusicDto;
import com.service.music.features.music.repository.MusicRepository;
import com.service.music.features.music.utility.MusicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;

    @Override
    public MusicDto getMusicDetailsById(String id) {
        log.info("MusicServiceImpl::getMusicDetailsById called with input: {}", id);
        if(Objects.isNull(id) || id.isBlank()) {
            log.error("MusicServiceImpl::getMusicDetailsById failed - ID is null or blank.");
            throw new GlobalDurinMusicServiceException("MusicServiceImpl::getMusicDetailsById failed - ID is null or blank.");
        }
        MusicModel musicModel = musicRepository.findById(id)
                .orElseThrow(()-> new GlobalDurinMusicServiceException("MusicServiceImpl::getMusicDetailsById failed - Music with ID: " + id + " does not exist."));
        return MusicMapper.mapMusicDetailsModelToDto(musicModel);
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
