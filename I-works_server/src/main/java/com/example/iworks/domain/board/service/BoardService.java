package com.example.iworks.domain.board.service;

import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;

import java.util.List;


public interface BoardService {

    //게시글 등록
    void createBoard(BoardCreateRequestDto boardCreateRequestDto);

    //게시글 수정
    void updateBoard(int boardId, BoardUpdateRequestDto boardUpdateRequestDto);

    //게시글 삭제
    void deleteBoard(int boardId);

    //게시글 전체 조회
    List<BoardGetResponseDto> getAll();

    //게시글 세부 조회
    BoardGetResponseDto getBoard(int boardId);

    //카테고리 별 게시글 전체 조회
    List<BoardGetResponseDto> getAllByCategory(int boardCategoryCodeId, int boardOwnerId);

    //카테고리 별 게시글 세부 조회
    BoardGetResponseDto getByCategory(int boardId, int boardCategoryCodeId, int boardOwnerId);

    //작성한 게시글 전체 조회
    List<BoardGetResponseDto> getAllByCreator(int boardCreatorId);

    //키워드 별 게시글 검색
    List<BoardGetResponseDto> getAllByKeyword(BoardSearchRequestDto keyword);

    //통합 키워드 별 게시글 검색
    List<BoardGetResponseDto> getAllByKeywords(String keywords);

    //게시글 북마크 등록/삭제
    Boolean updateBookmark(int boardId, String userEid);

    //북마크 된 게시글 전체 조회
    List<BoardGetResponseDto> getAllByBookmark(String userEid);

}
