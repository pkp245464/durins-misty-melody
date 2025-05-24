package com.service.notification.features.controller;

import com.service.notification.core.model.NotificationModel;
import com.service.notification.features.dto.NotificationRequest;
import com.service.notification.features.dto.ResendNotificationResponse;
import com.service.notification.features.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/notification-service")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/pending/{hours}")
    public ResponseEntity<List<NotificationModel>> getLastXHours(@PathVariable Integer hours) {
        log.info("NotificationController::getPendingNotifications received request for hours: {}", hours);
        List<NotificationModel> notifications = notificationService.getPendingNotificationsFromLastXHours(hours);
        log.info("NotificationController::getPendingNotifications returning {} notifications for hours: {}", notifications.size(), hours);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/ingest")
    public ResponseEntity<Boolean> sendNotification(@RequestBody NotificationRequest request) {
        log.info("NotificationController::sendNotification received request for userId: {}", request.getUserId());
        Boolean result = notificationService.sendNotificationFromMicroservice(request);
        log.info("NotificationController::sendNotification notification sent status: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send-pending")
    public ResponseEntity<ResendNotificationResponse> resendPendingEmails() {
        log.info("NotificationController::resendPendingEmails - Received request to resend pending emails");
        ResendNotificationResponse result = notificationService.resendPendingNotificationsFromLast24Hours();
        log.info("NotificationController::resendPendingEmails - Resend completed: total={}, sent={}, failed={}",
                result.getTotalNotifications(), result.getTotalSent(), result.getTotalFailed());
        return ResponseEntity.ok(result);
    }
}
