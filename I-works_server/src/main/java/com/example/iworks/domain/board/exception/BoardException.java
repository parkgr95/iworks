package com.example.iworks.domain.board.exception;

import com.example.iworks.global.exception.ErrorCode;
import com.example.iworks.global.exception.GlobalException;

public class BoardException extends GlobalException {

    public BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

}
