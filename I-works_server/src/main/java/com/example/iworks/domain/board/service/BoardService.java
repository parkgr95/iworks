package com.example.iworks.domain.board.service;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.board.dto.request.RequestBoard;
import com.example.iworks.domain.board.dto.request.SearchKeyword;
import com.example.iworks.domain.board.dto.response.ResponseBoard;
import com.example.iworks.global.model.entity.Code;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BoardService {

    //게시글 목록 전체 조회
    public List<ResponseBoard> findBoards();

    // 게시판 카테고리별 게시글 조회 (공지, 자유)
    public List<ResponseBoard> findAllByBoardCategoryCode(Code categoryCode);

    // 게시판 카테고리별 게시글 조회 (부서, 팀)
    public List<ResponseBoard> findAllByBoardCategoryCodeAndBoardOwnerId(Code categoryCode, int boardOwnerId);

    //게시글 작성
    @Transactional
    public void saveBoard(Board board);
    //게시글 수정
    @Transactional
    public void updateBoard(int boardId, RequestBoard requestBoard);

    //게시글 삭제
    @Transactional
    public void deleteBoard(int BoardId);

    // 게시글 검색
    public List<ResponseBoard> findAllByKeyword(SearchKeyword keyword);

    // 작성자가 작성한 게시글 검색
    public List<ResponseBoard> findAllByBoardCreatedId(int boardOwnerId);
    // 게시글 제목 검색
    public List<ResponseBoard> findAllByBoardTitle(String boardTitle);

    // 게시글 내용 검색
    public List<ResponseBoard> findAllByBoardContent(String boardContent);
    // 게시글 제목 + 내용 검색
    public List<ResponseBoard> findAllByBoardTitleOrBoardContent(String boardTitle, String boardContent);

}
