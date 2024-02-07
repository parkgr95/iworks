package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.notification.dto.notification.NotificationCreateRequestDto;

public interface NotificationService {
    void createNotification(NotificationCreateRequestDto notificationDto);
    void deleteNotification(int notificationId);
}
