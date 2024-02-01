package com.example.iworks.domain.comment.service;


import com.example.iworks.domain.comment.dto.reqeuest.CommentCreateRequestDto;
import com.example.iworks.domain.comment.dto.reqeuest.CommentUpdateRequestDto;
import com.example.iworks.domain.comment.dto.response.CommentGetResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    //댓글 등록
    public void createComment(CommentCreateRequestDto commentCreateRequestDto);

    //댓글 수정
    public void updateComment(int commentId, CommentUpdateRequestDto commentUpdateRequestDto);

    //댓글 삭제
    public void deleteComment(int commentId);

    //게시글별 댓글 전체 조회
    public Page<CommentGetResponseDto> getAllByBoardId(int boardId);

}
