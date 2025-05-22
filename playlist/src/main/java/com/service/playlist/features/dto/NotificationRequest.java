package com.service.playlist.features.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.playlist.core.enums.NotificationPriority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("email_id")
    private String emailId;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    @JsonProperty("priority")
    private NotificationPriority notificationPriority;
}
