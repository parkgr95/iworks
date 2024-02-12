package com.example.iworks.domain.board.repository.custom;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardSearchRepository {

    //키워드 별 게시글 검색
    List<Board> findAllByKeyword(Pageable pageable, BoardSearchRequestDto keyword);

    //통합 키워드 별 게시글 검색
    List<Board> findAllByKeywords(Pageable pageable, String keywords);

}
