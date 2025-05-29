package com.service.analytics.features.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicEventDto {
    private String userEventId;
    private String musicId;
    private Integer playCount;
    private List<LocalDateTime> playTimestamps;
}
