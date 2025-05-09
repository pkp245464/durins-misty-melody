package com.service.music.features.music.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioMetadataDto {
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("format")
    private String format;
    @JsonProperty("language")
    private String language;
    @JsonProperty("releaseDate")
    private Date releaseDate;
}
