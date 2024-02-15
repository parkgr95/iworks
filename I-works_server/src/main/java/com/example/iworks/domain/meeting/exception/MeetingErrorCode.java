package com.example.iworks.domain.meeting.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum MeetingErrorCode implements ErrorCode {

    MEETING_NOT_EXIST(400, "MEETING_01", "해당 회의가 존재하지 않습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    MeetingErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

}
