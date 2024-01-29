package com.example.iworks.domain.board.dto.response;

import com.example.iworks.domain.board.domain.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseBoard {

    private int boardId; // 글 아이디
    private int boardCreatorId; // 작성자 아이디
    private String boardTitle; // 제목
    private String boardContent; // 내용
    private LocalDateTime boardCreatedAt; // 작성일시
    private LocalDateTime boardUpdatedAt; // 수정일시

    public ResponseBoard(Board board) {
        this.boardId = board.getBoardId();
        this.boardCreatorId = board.getBoardCreatorId();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.boardCreatedAt = board.getBoardCreatedAt();
        this.boardUpdatedAt = board.getBoardUpdatedAt();
    }
}
