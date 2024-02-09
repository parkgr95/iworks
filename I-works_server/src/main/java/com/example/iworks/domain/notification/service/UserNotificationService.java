package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;

public interface UserNotificationService {
    void createUserNotification(UserNotificationCreateRequestDto createRequestDto);
    void deleteUserNotification(int userNotificationId);
}
