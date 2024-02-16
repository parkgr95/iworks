package com.example.iworks.domain.board.dto.request;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {

    private String boardTitle; //게시글 제목
    private String boardContent; //게시글 내용

}
