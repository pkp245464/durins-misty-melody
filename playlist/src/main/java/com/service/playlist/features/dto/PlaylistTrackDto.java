package com.service.playlist.features.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistTrackDto {

    @JsonProperty("playlist_track_id")
    private String playlistTrackId;

    @JsonProperty("music_id")
    private String musicId;

    @JsonProperty("position")
    private Integer position;

    @JsonProperty("added_date")
    private LocalDateTime addedDate;

    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;

    @JsonProperty("is_active")
    private Boolean isActive;
}
