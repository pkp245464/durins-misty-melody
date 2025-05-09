package com.service.music.features.music.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MusicDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("artist")
    private ArtistDto artist;
    @JsonProperty("stats")
    private StatsDto stats;
    @JsonProperty("visibility")
    private String visibility;
    @JsonProperty("tags")
    private List<String> tags;
    @JsonProperty("album")
    private AlbumDto album;
    @JsonProperty("mediaDetails")
    private MediaDetailsDto mediaDetails;
    @JsonProperty("audioMetadata")
    private AudioMetadataDto audioMetadata;
    @JsonProperty("uploadInfo")
    private UploadInfoDto uploadInfo;
}
