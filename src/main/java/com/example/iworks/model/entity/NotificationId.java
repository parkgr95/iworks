package com.example.iworks.model.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class NotificationId implements Serializable {
    private int categoryCode; //Notification.categoryCode 매핑
    private int ownerId;  //Notification.ownerId 매핑

    public NotificationId(int categoryCode, int ownerId) {
        this.categoryCode = categoryCode;
        this.ownerId = ownerId;
    }
    public NotificationId() {
    }
}
