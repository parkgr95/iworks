package com.example.iworks.domain.board.dto.response;

import com.example.iworks.domain.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardGetResponseDto {

    private final Integer boardId; //게시글 아이디
    private final Integer boardCategoryCodeId; //게시판 카테고리 아이디
    private final Integer boardOwnerId; //게시판 주체 아이디
    private final Integer boardCreatorId; //게시글 작성자 아이디
    private final String boardTitle; //게시글 제목
    private final String boardContent; //게시글 내용
    private final LocalDateTime boardCreatedAt; //게시글 작성 일시
    private final LocalDateTime boardUpdatedAt; //게시글 수정 일시

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
