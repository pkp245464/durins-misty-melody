package com.service.streaming.core.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transcode_audio")
public class TranscodeAudio {
    @Id
    private String id;
    private String musicId;
    private String originalFileUrl;
    private Map<String, String> qualityUrls;
    private LocalDateTime createdAt;
    private LocalDateTime lastChecked;
}
