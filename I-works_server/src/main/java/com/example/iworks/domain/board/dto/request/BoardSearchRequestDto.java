package com.example.iworks.domain.board.dto.request;

import lombok.Getter;

@Getter
public class BoardSearchRequestDto {

    private int boardCreatorId; //게시글 작성자 아이디
    private String boardTitle; //게시글 제목
    private String boardContent; //게시글 내용

}
