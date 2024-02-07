package com.example.iworks.domain.notification.repository.usernotification.custom;

import com.example.iworks.domain.notification.domain.UserNotification;

import java.util.List;

public interface UserNotificationSearchRepository {
    List<UserNotification> findByUserEid(String userEid);
}
