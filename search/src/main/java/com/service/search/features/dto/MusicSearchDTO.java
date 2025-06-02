package com.service.search.features.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MusicSearchDTO {
    private String musicId;
    private String title;
    private String artistName;
    private String genre;
    private String musicUrl;
}
