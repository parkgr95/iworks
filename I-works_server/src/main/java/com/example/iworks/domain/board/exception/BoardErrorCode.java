package com.example.iworks.domain.board.exception;

import com.example.iworks.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum BoardErrorCode implements ErrorCode {

    BOARD_NOT_EXIST(400, "error", "해당 게시글이 존재하지 않습니다."),
    BOARD_BY_CATEGORY_NOT_EXIST(400, "error", "카테고리 별 게시글이 존재하지 않습니다."),
    BOARD_BY_CREATOR_NOT_EXIST(400, "error", "작성한 게시글이 존재하지 않습니다."),
    BOARD_BY_KEYWORD_NOT_EXIST(400, "error", "키워드 별 게시글이 존재하지 않습니다."),
    BOARD_BY_BOOKMARK_NOT_EXIST(400, "error", "북마크 된 게시글이 존재하지 않습니다.");


    private final int statusCode;
    private final String result;
    private final String message;

    BoardErrorCode(int statusCode, String result, String message) {
        this.statusCode = statusCode;
        this.result = result;
        this.message = message;
    }

}
