package com.example.iworks.domain.board.dto.request;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.code.entity.Code;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardCreateRequestDto {

    private int boardCategoryCodeId; //카테고리 코드
    private int boardOwnerId; //게시판 주체 아이디
    private int boardCreatorId; //게시글 작성자 아이디
    private String boardTitle; //게시글 제목
    private String boardContent; //게시글 내용

    public Board toEntity(Code code) {
        return Board.builder()
                .boardCategoryCode(code)
                .boardOwnerId(boardOwnerId)
                .boardCreatorId(boardCreatorId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardCreatedAt(LocalDateTime.now())
                .boardUpdatedAt(LocalDateTime.now())
                .build();
    }

}
