package com.service.playlist.features.service;

import com.service.playlist.features.dto.CreatePlaylistRequest;
import com.service.playlist.features.dto.PlaylistDto;
import com.service.playlist.features.dto.PlaylistTrackDto;

public interface PlaylistService {
    PlaylistDto createPlaylist(CreatePlaylistRequest request);
    PlaylistDto getPlaylistById(String id);
    PlaylistTrackDto getPlaylistTrackById(String id);
}
