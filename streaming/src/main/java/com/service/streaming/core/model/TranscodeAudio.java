package com.service.streaming.core.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

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
    private String transcodeId;

    @Indexed(unique = true)
    private String musicId;

    private String originalFileUrl;
    private Map<String, String> transcodedUrls;
    private LocalDateTime originalReceivedAt;
    private LocalDateTime transcodedAt;
    private boolean isTranscoded;
}
