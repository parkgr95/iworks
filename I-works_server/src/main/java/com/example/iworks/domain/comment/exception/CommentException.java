package com.example.iworks.domain.comment.exception;

import com.example.iworks.global.exception.ErrorCode;
import com.example.iworks.global.exception.GlobalException;

public class CommentException extends GlobalException {

    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }

}
