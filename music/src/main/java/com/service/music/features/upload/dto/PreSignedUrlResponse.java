package com.service.music.features.upload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreSignedUrlResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("url")
    private String url;
}
