package com.example.iworks.user.model.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class UserNotificationId implements Serializable {
    private int user;
    private NotificationId notification;

    public UserNotificationId() {
    }

}
