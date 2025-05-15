package com.service.playlist.features.service;

import com.service.playlist.core.exceptions.GlobalDurinPlaylistServiceException;
import com.service.playlist.core.model.Playlist;
import com.service.playlist.core.model.PlaylistTrack;
import com.service.playlist.features.dto.CreatePlaylistRequest;
import com.service.playlist.features.dto.PlaylistDto;
import com.service.playlist.features.repository.PlaylistRepository;
import com.service.playlist.features.repository.PlaylistTrackRepository;
import com.service.playlist.features.utility.PlaylistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    private final WebClient webClient;

    private static final String USER_SERVICE_URL = "http://localhost:8081/durin's-misty-melody/user-service";
    private static final String MUSIC_SERVICE_URL = "http://localhost:8082/durin's-misty-melody/music-service";

    @Override
    public PlaylistDto createPlaylist(CreatePlaylistRequest request) {
        log.info("PlaylistServiceImpl::createPlaylist called with input: {}", request);

        if(Objects.isNull(request)) {
            throw new GlobalDurinPlaylistServiceException("PlaylistServiceImpl::createPlaylist failed - CreatePlaylistRequest is null");
        }

        validateUserIdFromUserMicroservice(request.getUserId());

        List<String>musicIds = request.getMusicIds();

        if(Objects.isNull(musicIds) || musicIds.isEmpty()) {
            throw new GlobalDurinPlaylistServiceException("PlaylistServiceImpl::createPlaylist failed - MusicIds is null or empty");
        }
        for (String musicId : musicIds) {
            validateMusicIdFromMusicMicroservice(musicId);
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

        return resultDto;
    }

    public void validateMusicIdFromMusicMicroservice(String musicId) {
        Boolean isUserExists = webClient.get()
                .uri(MUSIC_SERVICE_URL + "/validate-music-id/{musicId}", musicId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(isUserExists)) {
            log.error("UserServiceImpl::validateMusicIdFromMusicMicroservice failed - Music with ID: {} does not exist", musicId);
            throw new GlobalDurinPlaylistServiceException("MUSIC-SERVICE: Music with ID: " + musicId + " does not exist");
        }
    }

    public void validateUserIdFromUserMicroservice(String userId) {
        Boolean isUserExists = webClient.get()
                .uri(USER_SERVICE_URL + "/validate-user-id/{userId}", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if(Boolean.FALSE.equals(isUserExists)) {
            log.error("UserServiceImpl::validateUserIdFromUserMicroservice failed - User with ID: {} does not exist", userId);
            throw new GlobalDurinPlaylistServiceException("USER-SERVICE: User with ID: " + userId + " does not exist");
        }
    }
}
