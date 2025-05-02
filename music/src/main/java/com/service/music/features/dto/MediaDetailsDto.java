package com.service.music.features.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDetailsDto {
    private String fileUrl;
    private String coverImageUrl;
    private String lyrics;
}
