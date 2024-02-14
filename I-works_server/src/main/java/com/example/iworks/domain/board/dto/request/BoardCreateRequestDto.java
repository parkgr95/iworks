package com.example.iworks.domain.board.dto.request;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.code.entity.Code;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardCreateRequestDto {

    @NotNull
    private Integer boardCategoryCodeId; //카테고리 코드

    @NotNull
    private Integer boardOwnerId; //게시판 주체 아이디

    @NotBlank
    private String boardTitle; //게시글 제목

    @NotBlank
    private String boardContent; //게시글 내용

    public Board toEntity(Integer userId, Code code) {
        return Board.builder()
                .boardCategoryCode(code)
                .boardOwnerId(boardOwnerId)
                .boardCreatorId(userId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardCreatedAt(LocalDateTime.now())
                .boardUpdatedAt(LocalDateTime.now())
                .build();
    }

}
