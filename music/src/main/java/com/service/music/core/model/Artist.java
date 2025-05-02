package com.service.music.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    private String artistId;
    private String artistName;
    private String artistCountry;
}
