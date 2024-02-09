package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.notification.dto.usernotification.response.UserNotificationGetAllByUserResponseDto;
import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;

import java.util.List;

public interface UserNotificationService {

    void createUserNotification(UserNotificationCreateRequestDto createRequestDto);

    void deleteUserNotification(int userNotificationId);

    List<UserNotificationGetAllByUserResponseDto> getAllUserNotificationsByUserId(int userId);
}
