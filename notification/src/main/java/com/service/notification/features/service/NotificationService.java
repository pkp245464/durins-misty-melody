package com.service.notification.features.service;

import com.service.notification.core.model.NotificationModel;

import java.util.List;

public interface NotificationService {
    List<NotificationModel> getPendingNotificationsFromLastXHours(Integer hours);
    List<NotificationModel> resendPendingNotificationsFromLast24Hours();
}
