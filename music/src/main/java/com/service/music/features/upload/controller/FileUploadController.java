package com.service.music.features.upload.controller;

import com.service.music.core.exceptions.GlobalDurinMusicServiceException;
import com.service.music.features.upload.dto.PreSignedUrlRequest;
import com.service.music.features.upload.dto.PreSignedUrlResponse;
import com.service.music.features.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/music-service")
public class FileUploadController {

    private final UploadService uploadService;

    @PostMapping("/get-signed-url")
    public ResponseEntity<PreSignedUrlResponse> uploadfile(@RequestBody PreSignedUrlRequest preSignedUrlRequest) {
        PreSignedUrlResponse url = uploadService.getPreSignedUrl(preSignedUrlRequest.getFileName(), preSignedUrlRequest.getFileType(), preSignedUrlRequest.getSource(), preSignedUrlRequest.getSourceId());
        log.info("PublicFileController::uploadfile returning pre-signed url for file: {}", url.getId());
        return ResponseEntity.ok(url);
    }

    @PostMapping("/acknowledge")
    public ResponseEntity<Boolean> acknowledgeUpload(@RequestParam String fileId, @RequestParam String fileSize) {
        log.info("PublicFileController::acknowledgeUpload called with input: {}, {}", fileId, fileSize);
        return ResponseEntity.ok(uploadService.acknowledgeClientUpload(fileId, Long.valueOf(fileSize)));
    }

    @GetMapping("/get-public-url")
    public ResponseEntity<String> getFile(@RequestParam String fileId, @RequestParam Integer expiryDays) {
        String url = uploadService.getUrlWithExpiryAndId(fileId, expiryDays);
        if(Objects.isNull(url)) {
            log.info("PublicFileController::getFile failed - File not found for Id: {}", fileId);
            throw new GlobalDurinMusicServiceException("Failed to generate the public url");
        }
        log.info("PublicFileController::getFile returning public url for file: {}", url);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/get-public-urls")
    public ResponseEntity<Map<String, String>> getFiles(@RequestParam String fileIds, @RequestParam Integer expiryDays) {
        Map<String, String> urls = uploadService.getUrlWithExpiryForMultipleFiles(fileIds, expiryDays);
        if(Objects.isNull(urls)) {
            log.info("PublicFileController::getFiles failed - File not found for Ids: {}", fileIds);
            throw new GlobalDurinMusicServiceException("Failed to generate the public url");
        }
        log.info("PublicFileController::getFiles returning public urls for files: {}", urls);
        return ResponseEntity.ok(urls);
    }
}
