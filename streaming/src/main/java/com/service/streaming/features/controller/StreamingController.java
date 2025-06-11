package com.service.streaming.features.controller;

import com.service.streaming.features.service.StreamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/streaming-service")
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping("/original-file/{musicId}")
    public ResponseEntity<String> getOriginalFileUrl(@PathVariable String musicId) {
        log.debug("StreamingController::getOriginalFileUrl - Request received for musicId: {}", musicId);

        String fileUrl = streamingService.fetchAndStoreOriginalFileUrl(musicId);
        log.info("StreamingController::getOriginalFileUrl - Successfully processed request for musicId: {}", musicId);
        return ResponseEntity.ok(fileUrl);
    }

}
