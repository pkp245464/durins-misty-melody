package com.service.music.features.upload.service;

import com.service.music.features.upload.dto.PreSignedUrlResponse;

import java.util.Map;

public interface UploadService {
    PreSignedUrlResponse getPreSignedUrl(String fileName, String fileType, String source, String sourceId);
    Boolean acknowledgeClientUpload(String fileId, Long fileSize);
    String getUrlWithExpiryAndId(String id, Integer days);
    Map<String, String> getUrlWithExpiryForMultipleFiles(String ids, Integer days);
}
