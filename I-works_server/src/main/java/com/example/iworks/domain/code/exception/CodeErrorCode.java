package com.example.iworks.domain.code.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum CodeErrorCode implements ErrorCode {

    CODE_NOT_EXIST(400, "CODE_01", "해당 카테고리 코드가 존재하지 않습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    CodeErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

}
