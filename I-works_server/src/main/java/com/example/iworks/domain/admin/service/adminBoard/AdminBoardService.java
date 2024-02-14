package com.example.iworks.domain.admin.service.adminBoard;

import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;

public interface AdminBoardService {

    //게시글 수정
    void updateBoard(int boardId, int userId, BoardUpdateRequestDto boardUpdateRequestDto);

    //게시글 삭제
    void deleteBoard(int boardId, int userId);

}
