package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.notification.dto.usernotification.response.UserNotificationGetAllByUserResponseDto;
import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;

import java.util.List;

public interface UserNotificationService {

    void create(UserNotificationCreateRequestDto createRequestDto);

    void delete(int userNotificationId);

    List<UserNotificationGetAllByUserResponseDto> getAllByUserId(int userId);

    List<UserNotificationGetAllByUserResponseDto> getAllAboutBoardByUserId(int userId);
    List<UserNotificationGetAllByUserResponseDto> getAllAboutScheduleByUserId(int userId);
    List<UserNotificationGetAllByUserResponseDto> getAllAboutMeetingByUserId(int userId);

    long getCountIsNotSent(int userId);

    String getOneMessage(int userId);
}
