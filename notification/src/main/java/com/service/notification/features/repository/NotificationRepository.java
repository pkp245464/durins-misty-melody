package com.service.notification.features.repository;

import com.service.notification.core.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, String> {
    @Query("SELECT n FROM NotificationModel n WHERE n.deliveryStatus = 'PENDING' AND n.sendDate >= :fromTime")
    List<NotificationModel> findPendingNotificationsSince(@Param("fromTime") LocalDateTime fromTime);
}
