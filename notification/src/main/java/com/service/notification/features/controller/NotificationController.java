package com.service.notification.features.controller;

import com.service.notification.core.model.NotificationModel;
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

}
