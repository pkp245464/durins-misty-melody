package com.service.playlist.features.service;

import com.service.playlist.core.config.PlaylistUrlConfig;
import com.service.playlist.core.enums.NotificationPriority;
import com.service.playlist.core.exceptions.GlobalDurinPlaylistServiceException;
import com.service.playlist.core.model.Playlist;
import com.service.playlist.core.model.PlaylistTrack;
import com.service.playlist.features.dto.CreatePlaylistRequest;
import com.service.playlist.features.dto.NotificationRequest;
import com.service.playlist.features.dto.PlaylistDto;
import com.service.playlist.features.dto.PlaylistTrackDto;
import com.service.playlist.features.repository.PlaylistRepository;
import com.service.playlist.features.repository.PlaylistTrackRepository;
import com.service.playlist.features.utility.PlaylistCreationHandler;
import com.service.playlist.features.utility.PlaylistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService{

    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;
    private final PlaylistCreationHandler playlistCreationHandler;


    @Override
    public PlaylistDto createPlaylist(CreatePlaylistRequest request) {
        log.info("PlaylistServiceImpl::createPlaylist called with input: {}", request);

        if(Objects.isNull(request)) {
            throw new GlobalDurinPlaylistServiceException("PlaylistServiceImpl::createPlaylist failed - CreatePlaylistRequest is null");
        }

        playlistCreationHandler.validateUserIdFromUserMicroservice(request.getUserId());

        List<String>musicIds = request.getMusicIds();

        if(Objects.isNull(musicIds) || musicIds.isEmpty()) {
            throw new GlobalDurinPlaylistServiceException("PlaylistServiceImpl::createPlaylist failed - MusicIds is null or empty");
        }
        for (String musicId : musicIds) {
            playlistCreationHandler.validateMusicIdFromMusicMicroservice(musicId);
        }

        // Map CreatePlaylistRequest to Playlist entity
        Playlist playlist = PlaylistMapper.toEntity(request);

        // Save the playlist to get generated ID (if using generated IDs)
        Playlist savedPlaylist = playlistRepository.save(playlist);

        // Convert musicIds to PlaylistTrack entities and save them
        List<PlaylistTrack> tracks = musicIds.stream()
                .map(musicId -> {
                    PlaylistTrack track = new PlaylistTrack();
                    track.setMusicId(musicId);
                    track.setPlaylist(savedPlaylist);
                    track.setPosition(musicIds.indexOf(musicId) + 1);
                    track.setIsActive(true);
                    return track;
                })
                .collect(Collectors.toList());

        playlistTrackRepository.saveAll(tracks);

        savedPlaylist.setTracks(tracks);

        PlaylistDto resultDto = PlaylistMapper.toDto(savedPlaylist);
        log.info("PlaylistServiceImpl::createPlaylist successfully created playlist with ID: {}", resultDto.getPlaylistId());

        Integer noOfMusicInPlaylist = musicIds.size();

        playlistCreationHandler.dispatchPlaylistCreationNotificationMicroservices(request.getUserId(), request.getPlaylistName(), noOfMusicInPlaylist);

        return resultDto;
    }


    @Override
    public PlaylistDto getPlaylistById(String id) {
        log.info("PlaylistServiceImpl::getPlaylistById called with input: {}", id);
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new GlobalDurinPlaylistServiceException("Playlist not found with ID: " + id));
        log.info("PlaylistServiceImpl::getPlaylistById successfully getting playlist with ID: {}", id);
        return PlaylistMapper.toDto(playlist);
    }

    @Override
    public PlaylistTrackDto getPlaylistTrackById(String id) {
        log.info("PlaylistServiceImpl::getPlaylistTrackById called with id: {}", id);

        PlaylistTrack playlistTrack = playlistTrackRepository.findById(id)
                .orElseThrow(() -> new GlobalDurinPlaylistServiceException("Playlist track not found with ID: " + id));
        log.info("PlaylistServiceImpl::getPlaylistTrackById successfully getting playlistTrack with ID: {}", id);
        return PlaylistMapper.toTrackDto(playlistTrack);
    }
}
