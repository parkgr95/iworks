package com.example.iworks.domain.comment.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum CommentErrorCode implements ErrorCode {

    COMMMENT_NOT_EXIST(400, "COMMMENT_01", "해당 게시글이 존재하지 않습니다.");


    private final int statusCode;
    private final String errorCode;
    private final String message;

    CommentErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

}
