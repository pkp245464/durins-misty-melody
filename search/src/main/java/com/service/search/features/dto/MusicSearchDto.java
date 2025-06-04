package com.service.search.features.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MusicSearchDto {
    private String id;
    private String title;
    private String artistName;
    private List<String> tags;
    private String albumName;
}
