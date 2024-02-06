package com.example.iworks.domain.board.dto.response;

import com.example.iworks.domain.board.domain.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardGetResponseDto {

    private int boardId; //게시글 아이디
    private int boardCategoryCodeId; //게시판 카테고리 아이디
    private int boardOwnerId; //게시판 주체 아이디
    private int boardCreatorId; //게시글 작성자 아이디
    private String boardTitle; //게시글 제목
    private String boardContent; //게시글 내용
    private LocalDateTime boardCreatedAt; //게시글 작성 일시
    private LocalDateTime boardUpdatedAt; //게시글 수정 일시

    public BoardGetResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.boardCategoryCodeId = board.getBoardCategoryCode().getCodeId();
        this.boardOwnerId = board.getBoardOwnerId();
        this.boardCreatorId = board.getBoardCreatorId();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.boardCreatedAt = board.getBoardCreatedAt();
        this.boardUpdatedAt = board.getBoardUpdatedAt();
    }

}
