package com.example.iworks.global.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final Integer statusCode;
    private final String result;
    private final String message;

    public GlobalException(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.result = errorCode.getResult();
        this.message = errorCode.getMessage();
    }

}
