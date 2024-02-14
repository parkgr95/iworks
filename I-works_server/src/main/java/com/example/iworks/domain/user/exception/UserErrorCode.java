package com.example.iworks.domain.user.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum UserErrorCode implements ErrorCode {

    USER_IS_NOT_AUTHORIZATION(400, "USER_01", "권한 없는 유저입니다."),
    USER_NOT_EXIST(400, "USER_02", "해당 유저가 존재하지 않습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    UserErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

}
