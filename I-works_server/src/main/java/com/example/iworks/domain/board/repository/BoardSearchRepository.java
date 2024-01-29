package com.example.iworks.domain.board.repository;

import com.example.iworks.domain.board.dto.request.SearchKeyword;
import com.example.iworks.domain.board.dto.response.ResponseBoard;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BoardSearchRepository {

    // 게시글 검색
    List<ResponseBoard> findAllByKeyword(Pageable pageable, SearchKeyword keyword);

    // 작성자가 작성한 게시글 검색
    List<ResponseBoard> findAllByBoardCreatedId(Pageable pageable, int boardOwnerId);

    // 게시글 제목 검색
    List<ResponseBoard> findAllByBoardTitle(Pageable pageable, String boardTitle);

    // 게시글 내용 검색
    List<ResponseBoard> findAllByBoardContent(Pageable pageable, String boardContent);

    // 게시글 제목 + 내용 검색
    List<ResponseBoard> findAllByBoardTitleOrBoardContent(Pageable pageable, String boardTitle, String boardContent);
}
