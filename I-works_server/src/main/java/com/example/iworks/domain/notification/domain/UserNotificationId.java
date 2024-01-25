package com.example.iworks.domain.notification.domain;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class UserNotificationId implements Serializable {
    private int user;
    private NotificationId notification;

    public UserNotificationId() {
    }

}
