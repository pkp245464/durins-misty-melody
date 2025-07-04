package com.service.recommendation.features.dto;

import lombok.Data;

import java.util.List;

@Data
public class MusicDetailDto {
    private String musicId;
    private String title;
    private String artistName;
    private String albumName;
    private List<String> tags;
    private String fileUrl;
}
