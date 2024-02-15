package com.example.iworks.domain.comment.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum CommentErrorCode implements ErrorCode {

    COMMMENT_NOT_EXIST(400, "error", "해당 댓글이 존재하지 않습니다.");


    private final int statusCode;
    private final String result;
    private final String message;

    CommentErrorCode(int statusCode, String result, String message) {
        this.statusCode = statusCode;
        this.result = result;
        this.message = message;
    }

}
