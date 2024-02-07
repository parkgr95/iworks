package com.example.iworks.domain.notification.repository.usernotification;

import com.example.iworks.domain.notification.domain.UserNotification;
import com.example.iworks.domain.notification.repository.usernotification.custom.UserNotificationSearchRepository;

import java.util.List;

public class UserNotificationRepositoryImpl implements UserNotificationSearchRepository {
    @Override
    public List<UserNotification> findByUserEid(String userEid) {
        return null;
    }
}
