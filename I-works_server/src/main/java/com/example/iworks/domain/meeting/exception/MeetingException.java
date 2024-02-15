package com.example.iworks.domain.meeting.exception;

import com.example.iworks.global.exception.ErrorCode;
import com.example.iworks.global.exception.GlobalException;

public class MeetingException extends GlobalException {

    public MeetingException(ErrorCode errorCode) {
        super(errorCode);
    }

}
