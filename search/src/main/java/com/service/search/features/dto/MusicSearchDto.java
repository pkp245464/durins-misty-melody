package com.service.search.features.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
