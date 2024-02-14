package com.example.iworks.domain.user.exception;

import com.example.iworks.global.exception.ErrorCode;
import com.example.iworks.global.exception.GlobalException;

public class UserException extends GlobalException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
