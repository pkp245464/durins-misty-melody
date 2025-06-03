package com.service.music.features.music.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicSearchDto {
    private String id;
    private String title;
    private String artistName;
    private List<String> tags;
    private String albumName;
}
