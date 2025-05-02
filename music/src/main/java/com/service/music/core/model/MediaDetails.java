package com.service.music.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDetails {
    private String fileUrl;
    private String coverImageUrl;
    private String lyrics;
}
