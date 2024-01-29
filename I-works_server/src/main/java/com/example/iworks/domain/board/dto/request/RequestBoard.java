package com.example.iworks.domain.board.dto.request;

import com.example.iworks.domain.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestBoard {

    private int boardId;
    private int boardCreatorId; // 작성자 아이디
    private String boardTitle; // 제목
    private String boardContent; // 내용

    public Board toEntity() {
        return Board.builder()
                .boardCreatorId(boardCreatorId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .build();
    }
}
