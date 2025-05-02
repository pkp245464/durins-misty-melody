package com.service.music.features.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioMetadataDto {
    private Integer duration;
    private String format;
    private String language;
    private Date releaseDate;
}
