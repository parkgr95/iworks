package com.example.iworks.domain.board.repository.custom;

import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BoardSearchRepository {

    //키워드별 게시글 검색
    List<BoardGetResponseDto> findAllByKeyword(Pageable pageable, BoardSearchRequestDto keyword);

    //통합 키워드별 게시글 검색
    List<BoardGetResponseDto> findAllByKeywords(Pageable pageable, String keywords);

}
