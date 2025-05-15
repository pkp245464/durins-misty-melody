package com.service.playlist.features.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDto {

    @JsonProperty("playlist_id")
    private String playlistId;

    @JsonProperty("playlist_name")
    private String playlistName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("playlist_description")
    private String playlistDescription;

    @JsonProperty("cover_image_url")
    private String coverImageUrl;

    @JsonProperty("visibility")
    private Boolean visibility;

    @JsonProperty("created_at")
    private LocalDateTime createdDate;

    @JsonProperty("updated_at")
    private LocalDateTime updatedDate;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("tracks")
    private List<PlaylistTrackDto> tracks;
}
