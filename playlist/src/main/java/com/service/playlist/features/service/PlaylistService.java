package com.service.playlist.features.service;

import com.service.playlist.features.dto.CreatePlaylistRequest;
import com.service.playlist.features.dto.PlaylistDto;

public interface PlaylistService {
    PlaylistDto createPlaylist(CreatePlaylistRequest request);
}
