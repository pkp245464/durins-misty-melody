package com.service.notification.features.service;

import com.service.notification.core.model.NotificationModel;
import com.service.notification.features.dto.NotificationRequest;

import java.util.List;

public interface NotificationService {
    List<NotificationModel> getPendingNotificationsFromLastXHours(Integer hours);

    // Mail Sender
    List<NotificationModel> resendPendingNotificationsFromLast24Hours();

    // API used by external microservices to send notifications
    Boolean sendNotificationFromMicroservice(NotificationRequest notificationRequest);
}
