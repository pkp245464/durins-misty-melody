package com.service.analytics.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "music_events")
public class MusicEvent {
    @Id
    private String userEventId;
    private String musicId;
    private Integer playCount = 0;
    private List<LocalDateTime>playTimestamps;
}
