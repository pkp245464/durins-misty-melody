package com.service.notification.features.service;

import com.service.notification.core.enums.DeliveryStatus;
import com.service.notification.core.exceptions.GlobalDurinNotificationServiceException;
import com.service.notification.core.model.NotificationModel;
import com.service.notification.features.dto.NotificationRequest;
import com.service.notification.features.dto.ResendNotificationResponse;
import com.service.notification.features.repository.NotificationRepository;
import com.service.notification.features.utility.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MailSender mailSender;

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
    public ResendNotificationResponse resendPendingNotificationsFromLast24Hours() {
        LocalDateTime fromTime = LocalDateTime.now().minusHours(24);
        log.info("NotificationServiceImpl::resendPendingNotificationsFromLast24Hours - Fetching pending notifications since: {}", fromTime);

        List<NotificationModel> pendingNotifications = notificationRepository.findPendingNotificationsSince(fromTime);
        log.info("NotificationServiceImpl::resendPendingNotificationsFromLast24Hours - Found {} pending notifications", pendingNotifications.size());

        List<String> sent = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        for (NotificationModel notification : pendingNotifications) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setFrom("pankajkumar245464@gmail.com");
                msg.setTo(notification.getEmailId());
                msg.setSubject(notification.getSubject());
                msg.setText(notification.getMessage());

                mailSender.send(msg);
                notification.setDeliveryStatus(DeliveryStatus.SENT);
                sent.add(notification.getEmailId());
                log.info("NotificationServiceImpl::resendPendingNotificationsFromLast24Hours - Email sent to {}", notification.getEmailId());
            }
            catch (Exception e) {
                notification.setDeliveryStatus(DeliveryStatus.FAILED);
                failed.add(notification.getEmailId());
                log.error("NotificationServiceImpl::resendPendingNotificationsFromLast24Hours - Failed to send email to {}: {}", notification.getEmailId(), e.getMessage());
            }
        }

        notificationRepository.saveAll(pendingNotifications);

        log.info("NotificationServiceImpl::resendPendingNotificationsFromLast24Hours - Resend summary: total={}, sent={}, failed={}",
                pendingNotifications.size(), sent.size(), failed.size());

        return ResendNotificationResponse.builder()
                .totalNotifications(pendingNotifications.size())
                .totalSent(sent.size())
                .totalFailed(failed.size())
                .sentTo(sent)
                .failedTo(failed)
                .build();
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
