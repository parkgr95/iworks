package com.example.iworks.model.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class UserNotificationId implements Serializable {
    private NotificationId notification;
    private String user;
    public UserNotificationId() {
    }
    public UserNotificationId(NotificationId notification, String user) {
        this.notification = notification;
        this.user = user;
    }
}
