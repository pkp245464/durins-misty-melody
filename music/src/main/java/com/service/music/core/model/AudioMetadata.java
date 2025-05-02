package com.service.music.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioMetadata {
    private Integer duration;
    private String format;
    private String language;
    private Date releaseDate;
}
