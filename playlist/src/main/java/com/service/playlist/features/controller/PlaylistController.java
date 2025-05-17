package com.service.playlist.features.controller;

import com.service.playlist.features.dto.CreatePlaylistRequest;
import com.service.playlist.features.dto.PlaylistDto;
import com.service.playlist.features.dto.PlaylistTrackDto;
import com.service.playlist.features.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/playlist-service")
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping("/registered")
    public ResponseEntity<PlaylistDto> createPlaylist(@RequestBody CreatePlaylistRequest request) {
        log.info("PlaylistController::createPlaylist called with input: {}", request);
        PlaylistDto response = playlistService.createPlaylist(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/playlist/{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable String id) {
        log.info("PlaylistController::getPlaylistById called with id: {}", id);
        PlaylistDto playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/playlist-track/{id}")
    public ResponseEntity<PlaylistTrackDto> getPlaylistTrackById(@PathVariable String id) {
        log.info("PlaylistController::getPlaylistTrackById called with id: {}", id);
        PlaylistTrackDto track = playlistService.getPlaylistTrackById(id);
        return ResponseEntity.ok(track);
    }
}
