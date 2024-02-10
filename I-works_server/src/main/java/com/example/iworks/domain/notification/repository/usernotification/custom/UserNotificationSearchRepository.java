package com.example.iworks.domain.notification.repository.usernotification.custom;

import com.example.iworks.domain.notification.domain.UserNotification;

import java.util.List;

public interface UserNotificationSearchRepository {
    List<UserNotification> findByUserId(int userId);

    List<UserNotification> findCategoryBoardByUserId(int userId);

    List<UserNotification> findCategoryScheduleByUserId(int userId);

    List<UserNotification> findCategoryMeetingByUserId(int userId);

}
