package com.example.iworks.domain.notification.repository.usernotification.custom;

import com.example.iworks.domain.notification.domain.UserNotification;

import java.util.List;

public interface UserNotificationSearchRepository {
    List<UserNotification> findAllByUserId(int userId);

    List<UserNotification> findAllCategoryBoardByUserId(int userId);

    List<UserNotification> findAllCategoryScheduleByUserId(int userId);

    List<UserNotification> findAllCategoryMeetingByUserId(int userId);

    long countOfIsNotSent(int userId);

}
