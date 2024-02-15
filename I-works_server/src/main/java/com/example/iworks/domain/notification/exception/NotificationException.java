package com.example.iworks.domain.notification.exception;

import com.example.iworks.global.exception.ErrorCode;
import com.example.iworks.global.exception.GlobalException;

public class NotificationException extends GlobalException {

    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
