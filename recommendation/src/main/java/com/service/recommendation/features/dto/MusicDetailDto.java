package com.service.recommendation.features.dto;

import lombok.Data;

@Data
public class MusicDetailDto {
    private String id;
    private String title;
    private String artistName;
    private String albumName;
    private String genre;
    private String fileUrl;
}
