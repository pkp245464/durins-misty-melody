package com.service.notification.features.service;

import com.service.notification.core.model.NotificationModel;
import com.service.notification.features.dto.NotificationRequest;
import com.service.notification.features.dto.ResendNotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationModel> getPendingNotificationsFromLastXHours(Integer hours);

    // Mail Sender
    ResendNotificationResponse resendPendingNotificationsFromLast24Hours();

    // API used by external microservices to send notifications
    Boolean sendNotificationFromMicroservice(NotificationRequest notificationRequest);
}
