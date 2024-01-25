package com.example.iworks.notification.model.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class UserNotificationId implements Serializable {
    private int user;
    private NotificationId notification;

    public UserNotificationId() {
    }

}
