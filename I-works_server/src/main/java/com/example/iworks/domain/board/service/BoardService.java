package com.example.iworks.domain.board.service;

import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;

import java.util.List;


public interface BoardService {

    //게시글 등록
    public void createBoard(BoardCreateRequestDto boardCreateRequestDto);

    //게시글 수정
    public void updateBoard(int boardId, BoardUpdateRequestDto boardUpdateRequestDto);

    //게시글 삭제
    public void deleteBoard(int boardId);

    //게시글 전체 조회
    public List<BoardGetResponseDto> getAll();

    //게시글 세부 조회
    public BoardGetResponseDto getBoard(int boardId);

    //카테고리별 게시글 전체 조회
    public List<BoardGetResponseDto> getAllByCategory(int boardCategoryCodeId, int boardOwnerId);

    //카테고리별 게시글 세부 조회
    public BoardGetResponseDto getByCategory(int boardId, int boardCategoryCodeId, int boardOwnerId);

    //작성한 게시글 전체 조회
    List<BoardGetResponseDto> getAllByCreator(int boardCreatorId);

    //키워드별 게시글 검색
    public List<BoardGetResponseDto> getAllByKeyword(BoardSearchRequestDto keyword);

    //통합 키워드별 게시글 검색
    public List<BoardGetResponseDto> getAllByKeywords(String keywords);

    //게시글 북마크 등록/삭제
    public void updateBookmark(int boardId, String userEid);

}
