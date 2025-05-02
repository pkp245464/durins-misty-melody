package com.service.music.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadInfo {
    private String uploadedBy;
    private Date uploadDate;
}
