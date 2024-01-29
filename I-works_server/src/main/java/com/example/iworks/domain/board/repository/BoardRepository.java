package com.example.iworks.domain.board.repository;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.global.model.entity.Code;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearchRepository {

    // 게시판 카테고리별 게시글 조회 (공지, 자유)
    @Query("select b from Board b order by b.boardUpdatedAt desc")
    List<Board> findAllByBoardCategoryCode(Pageable pageable, Code categoryCode);

    // 게시판 카테고리별 게시글 조회 (부서, 팀)
    @Query("select b from Board b order by b.boardUpdatedAt desc")
    List<Board> findAllByBoardCategoryCodeAndBoardOwnerId(Pageable pageable, Code categoryCode, int boardOwnerId);
}
