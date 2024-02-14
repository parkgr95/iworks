package com.example.iworks.domain.comment.repository;


import com.example.iworks.domain.comment.domain.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentSearchRepository {

    //게시판 별 댓글 전체 조회
    List<Comment> findAllByBoard(Pageable pageable, int boardId);

}
