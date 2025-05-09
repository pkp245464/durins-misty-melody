package com.service.music.features.music.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaDetailsDto {
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("cover_image_url")
    private String coverImageUrl;
    @JsonProperty("lyrics")
    private String lyrics;
}
