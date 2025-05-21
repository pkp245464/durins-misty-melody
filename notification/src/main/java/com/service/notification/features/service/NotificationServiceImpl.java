package com.service.notification.features.service;

import com.service.notification.core.exceptions.GlobalDurinNotificationServiceException;
import com.service.notification.core.model.NotificationModel;
import com.service.notification.features.dto.NotificationRequest;
import com.service.notification.features.repository.NotificationRepository;
import com.service.notification.features.utility.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationModel> getPendingNotificationsFromLastXHours(Integer hours) {
        if(Objects.isNull(hours)) {
            hours = 24;
            log.warn("NotificationServiceImpl::Invalid hours provided, defaulting to 24 hours.");
        }
        log.info("NotificationServiceImpl::getPendingNotificationsFromLastXHours received request for hours: {}", hours);
        if(hours < 1 || hours > 81) {
            log.error("NotificationServiceImpl::Hours must be between 1 and 81. Invalid hours: {}", hours);
            throw new GlobalDurinNotificationServiceException("Hours must be between 1 and 81. Invalid hours: " + hours);
        }
        LocalDateTime fromTime = LocalDateTime.now().minusHours(hours);
        log.info("NotificationServiceImpl::getPendingNotificationsFromLastXHours processing notifications from time: {}", fromTime);
        List<NotificationModel> notifications = notificationRepository.findPendingNotificationsSince(fromTime);
        log.info("NotificationServiceImpl::getPendingNotificationsFromLastXHours returning {} notifications for hours: {}", notifications.size(), hours);
        return notifications;
    }

    @Override
    public List<NotificationModel> resendPendingNotificationsFromLast24Hours() {
        return List.of();
    }

    // Note: user_id and email verification are performed in their respective microservices
    @Override
    public Boolean sendNotificationFromMicroservice(NotificationRequest notificationRequest) {
        if(Objects.isNull(notificationRequest)) {
            log.error("NotificationServiceImpl::sendNotificationFromMicroservice - Received null request");
            return false;
        }
        log.info("NotificationServiceImpl::sendNotificationFromMicroservice - Processing request for userId: {}", notificationRequest.getUserId());
        NotificationModel notificationModel = NotificationMapper.toEntity(notificationRequest);
        notificationRepository.save(notificationModel);
        log.info("NotificationServiceImpl::sendNotificationFromMicroservice - Notification saved successfully with emailId: {}", notificationModel.getEmailId());
        return true;
    }
}
