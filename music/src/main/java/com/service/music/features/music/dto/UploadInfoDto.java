package com.service.music.features.music.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadInfoDto {
    @JsonProperty("uploaded_by")
    private String uploadedBy;
    @JsonProperty("upload_date")
    private Date uploadDate;
}
