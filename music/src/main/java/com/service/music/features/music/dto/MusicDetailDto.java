package com.service.music.features.music.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MusicDetailDto {
    private String musicId;
    private String title;
    private String artistName;
    private String albumName;
    private List<String> tags;
    private String fileUrl;
}
