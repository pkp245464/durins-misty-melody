package com.service.music.features.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadInfoDto {
    private String uploadedBy;
    private Date uploadDate;
}
