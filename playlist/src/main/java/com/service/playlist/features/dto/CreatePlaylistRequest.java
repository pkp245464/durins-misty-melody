package com.service.playlist.features.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePlaylistRequest {

    @JsonProperty("playlist_name")
    private String playlistName;

    @JsonProperty("playlist_description")
    private String playlistDescription;

    @JsonProperty("cover_image_url")
    private String coverImageUrl;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("music_ids")
    private List<String> musicIds;
}
