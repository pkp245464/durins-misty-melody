package com.service.notification.features.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResendNotificationResponse {
    private Integer totalNotifications;
    private Integer totalSent;
    private Integer totalFailed;
    private List<String> sentTo;
    private List<String> failedTo;
}
