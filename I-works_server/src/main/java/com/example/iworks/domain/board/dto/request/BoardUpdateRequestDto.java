package com.example.iworks.domain.board.dto.request;

import com.example.iworks.domain.board.domain.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardUpdateRequestDto {

    private int boardModifierId; // 게시글 수정자 아이디
    private String boardTitle; // 게시글 제목
    private String boardContent; // 게시글 내용

}
