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
public class PreSignedUrlRequest {
    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_type")
    private String fileType;

    @JsonProperty("source")
    private String source;

    @JsonProperty("source_id")
    private String sourceId;
}
