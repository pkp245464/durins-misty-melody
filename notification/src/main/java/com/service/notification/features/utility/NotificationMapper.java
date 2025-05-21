package com.service.notification.features.utility;

import com.service.notification.core.enums.DeliveryStatus;
import com.service.notification.core.enums.NotificationPriority;
import com.service.notification.core.exceptions.GlobalDurinNotificationServiceException;
import com.service.notification.core.model.NotificationModel;
import com.service.notification.features.dto.NotificationRequest;

import java.util.Objects;

public class NotificationMapper {
    public static NotificationModel toEntity(NotificationRequest request) {
        NotificationModel notificationModel = new NotificationModel();

        validateUserId(request.getUserId());
        notificationModel.setUserId(request.getUserId());

        validateEmailId(request.getEmailId());
        notificationModel.setEmailId(request.getEmailId());

        validateMessage(request.getMessage());
        notificationModel.setSubject(request.getSubject());

        notificationModel.setMessage(request.getMessage());
        notificationModel.setNotificationPriority(mapPriorityNotification(request.getNotificationPriority()));
        notificationModel.setDeliveryStatus(DeliveryStatus.PENDING);
        return notificationModel;
    }

    private static NotificationPriority mapPriorityNotification(NotificationPriority priority) {
        return priority != null ? priority : NotificationPriority.NORMAL;
    }

    private static void validateUserId(String userId) {
        if (Objects.isNull(userId) || userId.isBlank()) {
            throw new GlobalDurinNotificationServiceException("NotificationMapper::validateUserId - userId must not be null or empty");
        }
    }

    private static void validateEmailId(String emailId) {
        if (Objects.isNull(emailId) || emailId.isBlank()) {
            throw new GlobalDurinNotificationServiceException("NotificationMapper::validateEmailId - emailId must not be null or empty");
        }
    }

    private static void validateMessage(String message) {
        if (Objects.isNull(message) || message.isBlank()) {
            throw new GlobalDurinNotificationServiceException("NotificationMapper::validateMessage - message must not be null or empty");
        }
    }
}
