package com.example.iworks.domain.comment.service;


import com.example.iworks.domain.comment.dto.reqeuest.CommentCreateRequestDto;
import com.example.iworks.domain.comment.dto.reqeuest.CommentUpdateRequestDto;
import com.example.iworks.domain.comment.dto.response.CommentGetResponseDto;

import java.util.List;

public interface CommentService {

    //댓글 등록
    void createComment(int userId, CommentCreateRequestDto commentCreateRequestDto);

    //댓글 수정
    void updateComment(int commentId, int userId, CommentUpdateRequestDto commentUpdateRequestDto);

    //댓글 삭제
    void deleteComment(int commentId, int userId);

    //게시글 별 댓글 전체 조회
    List<CommentGetResponseDto> getAllByBoard(int boardId);

}
