package com.example.iworks.domain.code.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum CodeErrorCode implements ErrorCode {

    CODE_NOT_EXIST(400, "error", "해당 카테고리 코드가 존재하지 않습니다.");

    private final int statusCode;
    private final String result;
    private final String message;

    CodeErrorCode(int statusCode, String result, String message) {
        this.statusCode = statusCode;
        this.result = result;
        this.message = message;
    }

}
