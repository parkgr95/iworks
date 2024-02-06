package com.example.iworks.domain.notification.repository.usernotification;

import com.example.iworks.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<Notification, Integer> {
}
