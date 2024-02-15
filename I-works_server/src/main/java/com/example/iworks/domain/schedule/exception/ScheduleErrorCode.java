package com.example.iworks.domain.schedule.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum ScheduleErrorCode implements ErrorCode {

    SCHEDULE_NOT_EXIST(400, "SCHEDULE_01", "해당 할일이 존재하지 않습니다."),
    SCHEDULE_BY_CATEGORY_NOT_EXIST(400, "SCHEDULE_02", "해당 할일 배정이 존재하지 않습니다."),
    SCHEDULE_IS_DELETED(400, "SCHEDULE_03", "삭제된 할일입니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    ScheduleErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

}
