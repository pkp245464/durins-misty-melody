package com.service.analytics.core.model;

import com.service.analytics.core.enums.PlaybackAction;
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
@Document(collection = "user_events")
public class UserEvent {
    @Id
    private String userEventId;
    private String userId;
    private String musicId;
    private Integer playCount = 0;
    private List<LocalDateTime>playTimestamps;
}
