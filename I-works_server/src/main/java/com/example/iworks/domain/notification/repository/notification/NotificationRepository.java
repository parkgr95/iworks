package com.example.iworks.domain.notification.repository.notification;

import com.example.iworks.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
