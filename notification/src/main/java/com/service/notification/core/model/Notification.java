package com.service.notification.core.model;

import com.service.notification.core.enums.DeliveryStatus;
import com.service.notification.core.enums.NotificationPriority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    @Column(name = "notifications_priority")
    @Enumerated(EnumType.STRING)
    private NotificationPriority notificationPriority =  NotificationPriority.NORMAL;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
