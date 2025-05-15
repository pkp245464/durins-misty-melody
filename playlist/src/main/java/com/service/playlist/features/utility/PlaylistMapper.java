package com.service.playlist.features.utility;

import com.service.playlist.core.model.Playlist;
import com.service.playlist.core.model.PlaylistTrack;
import com.service.playlist.features.dto.CreatePlaylistRequest;
import com.service.playlist.features.dto.PlaylistDto;
import com.service.playlist.features.dto.PlaylistTrackDto;

import java.util.List;
import java.util.stream.Collectors;

public class PlaylistMapper {
    // Convert Playlist entity to PlaylistDto
    public static PlaylistDto toDto(Playlist playlist) {
        PlaylistDto dto = new PlaylistDto();
        dto.setPlaylistId(playlist.getPlaylistId());
        dto.setPlaylistName(playlist.getPlaylistName());
        dto.setUserId(playlist.getUserId());
        dto.setPlaylistDescription(playlist.getPlaylistDescription());
        dto.setCoverImageUrl(playlist.getCoverImageUrl());
        dto.setVisibility(playlist.getVisibility());
        dto.setCreatedDate(playlist.getCreatedDate());
        dto.setUpdatedDate(playlist.getUpdatedDate());
        dto.setIsActive(playlist.getIsActive());

        if (playlist.getTracks() != null) {
            List<PlaylistTrackDto> trackDtos = playlist.getTracks().stream()
                    .map(PlaylistMapper::toTrackDto)
                    .collect(Collectors.toList());
            dto.setTracks(trackDtos);
        }

        return dto;
    }

    // Convert PlaylistDto to Playlist entity
    public static Playlist toEntity(PlaylistDto dto) {
        Playlist playlist = new Playlist();
        playlist.setPlaylistId(dto.getPlaylistId());
        playlist.setPlaylistName(dto.getPlaylistName());
        playlist.setUserId(dto.getUserId());
        playlist.setPlaylistDescription(dto.getPlaylistDescription());
        playlist.setCoverImageUrl(dto.getCoverImageUrl());
        playlist.setVisibility(dto.getVisibility());
        playlist.setIsActive(dto.getIsActive());

        return playlist;
    }

    // Convert PlaylistTrack entity to PlaylistTrackDto
    public static PlaylistTrackDto toTrackDto(PlaylistTrack track) {
        PlaylistTrackDto dto = new PlaylistTrackDto();
        dto.setPlaylistTrackId(track.getPlaylistTrackId());
        dto.setMusicId(track.getMusicId());
        dto.setPosition(track.getPosition());
        dto.setAddedDate(track.getAddedDate());
        dto.setUpdatedDate(track.getUpdatedDate());
        dto.setIsActive(track.getIsActive());
        return dto;
    }

    // Convert PlaylistTrackDto to PlaylistTrack entity, assigning parent Playlist
    public static PlaylistTrack toTrackEntity(PlaylistTrackDto dto, Playlist playlist) {
        PlaylistTrack track = new PlaylistTrack();
        track.setPlaylistTrackId(dto.getPlaylistTrackId());
        track.setMusicId(dto.getMusicId());
        track.setPosition(dto.getPosition());
        track.setIsActive(dto.getIsActive());
        track.setPlaylist(playlist);
        return track;
    }

    // Convert CreatePlaylistRequest DTO to Playlist entity (for creation)
    public static Playlist toEntity(CreatePlaylistRequest request) {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName(request.getPlaylistName());
        playlist.setPlaylistDescription(request.getPlaylistDescription());
        playlist.setCoverImageUrl(request.getCoverImageUrl());
        playlist.setUserId(request.getUserId());
        playlist.setIsActive(true);
        playlist.setVisibility(true);
        return playlist;
    }
}
