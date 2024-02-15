package com.example.iworks.domain.board.repository.custom;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.code.entity.Code;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardGetRepository {

    //카테고리 별 게시글 전체 조회
    List<Board> findAllByCategory(Pageable pageable, Code boardCategoryCode, int boardOwnerId);

    //카테고리 별 게시글 세부 조회
    Board findByCategory(int boardId, Code boardCategoryCode, int boardOwnerId);

    //작성한 게시글 전체 조회
    List<Board> findAllByCreator(Pageable pageable, int boardCreatorId);

    //북마크 한 게시글 전체 조회
    List<Board> findAllByBookmark(Pageable pageable, int userId);

}
