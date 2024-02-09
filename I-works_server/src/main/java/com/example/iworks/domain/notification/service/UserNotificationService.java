package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;

public interface UserNotificationService {
    void createNotification(UserNotificationCreateRequestDto notificationDto);
    void deleteNotification(int notificationId);
}
