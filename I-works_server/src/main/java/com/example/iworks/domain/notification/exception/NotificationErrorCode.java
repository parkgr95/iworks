package com.example.iworks.domain.notification.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum NotificationErrorCode implements ErrorCode {

    NOTIFICATION_NOT_EXIST(400, "NOTIFICATION_01", "해당 알람이 존재하지 않습니다."),
    USER_NOTIFICATION_NOT_EXIST(400, "NOTIFICATION_02", "해당 유저의 알림이 존재하지 않습니다.");

    private final int statusCode;
    private final String result;
    private final String message;

    NotificationErrorCode(int statusCode, String result, String message) {
        this.statusCode = statusCode;
        this.result = result;
        this.message = message;
    }

}
