package com.example.iworks.domain.comment.repository;


import com.example.iworks.domain.comment.dto.response.CommentGetResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentSearchRepository {

    //게시판 별 댓글 전체 조회
    Page<CommentGetResponseDto> findAllByBoard(Pageable pageable, int boardId);
}
