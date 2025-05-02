package com.service.music.features.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicDto {
    private String id;
    private String title;
    private ArtistDto artist;
    private StatsDto stats;
    private String visibility;
    private List<String> tags;
    private AlbumDto album;
    private MediaDetailsDto mediaDetails;
    private AudioMetadataDto audioMetadata;
    private UploadInfoDto uploadInfo;
}
