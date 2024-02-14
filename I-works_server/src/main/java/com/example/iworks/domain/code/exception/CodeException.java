package com.example.iworks.domain.code.exception;

import com.example.iworks.global.exception.ErrorCode;
import com.example.iworks.global.exception.GlobalException;

public class CodeException extends GlobalException {

    public CodeException(ErrorCode errorCode) {
        super(errorCode);
    }

}
