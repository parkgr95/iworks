package com.example.iworks.domain.schedule.exception;

import com.example.iworks.global.exception.ErrorCode;
import com.example.iworks.global.exception.GlobalException;

public class ScheduleException extends GlobalException {

    public ScheduleException(ErrorCode errorCode) {
        super(errorCode);
    }

}
