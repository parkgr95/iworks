package com.example.iworks.domain.board.service;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.board.dto.request.RequestBoard;
import com.example.iworks.domain.board.dto.request.SearchKeyword;
import com.example.iworks.domain.board.dto.response.ResponseBoard;
import com.example.iworks.domain.board.repository.BoardRepository;
import com.example.iworks.global.model.entity.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final PageRequest pageRequest = PageRequest.of(0, 10);

    //게시글 목록 전체 조회
    public List<ResponseBoard> findBoards() {
        Page<Board> boards = boardRepository.findAll(pageRequest);
        return boards.stream()
                .map(ResponseBoard::new)
                .collect(toList());
    }

    // 게시판 카테고리별 게시글 조회 (공지, 자유)
    public List<ResponseBoard> findAllByBoardCategoryCode(Code categoryCode) {
        List<Board> boards = boardRepository.findAllByBoardCategoryCode(pageRequest, categoryCode);
        return boards.stream()
                .map(ResponseBoard::new)
                .collect(toList());
    }

    // 게시판 카테고리별 게시글 조회 (부서, 팀)
    public List<ResponseBoard> findAllByBoardCategoryCodeAndBoardOwnerId(Code categoryCode, int boardOwnerId) {
        List<Board> boards = boardRepository.findAllByBoardCategoryCodeAndBoardOwnerId(pageRequest, categoryCode, boardOwnerId);
        return boards.stream()
                .map(ResponseBoard::new)
                .collect(toList());
    }

    //게시글 작성
    @Transactional
    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    //게시글 수정
    @Transactional
    public void updateBoard(int boardId, RequestBoard requestBoard) {
        Board findBoard = boardRepository.findById((long) boardId)
                .orElseThrow(IllegalStateException::new); //예외 메서드 추가
        findBoard.updateBoard(findBoard);
    }

    //게시글 삭제
    @Transactional
    public void deleteBoard(int BoardId) {
        Board findBoard = boardRepository.findById((long) BoardId)
                .orElseThrow(IllegalStateException::new);
        boardRepository.delete(findBoard);
    }

    // 게시글 검색
    public List<ResponseBoard> findAllByKeyword(SearchKeyword keyword) {
        return boardRepository.findAllByKeyword(pageRequest, keyword);
    }

    // 작성자가 작성한 게시글 검색
    public List<ResponseBoard> findAllByBoardCreatedId(int boardOwnerId) {
        return boardRepository.findAllByBoardCreatedId(pageRequest, boardOwnerId);
    }

    // 게시글 제목 검색
    public List<ResponseBoard> findAllByBoardTitle(String boardTitle) {
        return boardRepository.findAllByBoardTitle(pageRequest, boardTitle);
    }

    // 게시글 내용 검색
    public List<ResponseBoard> findAllByBoardContent(String boardContent) {
        return boardRepository.findAllByBoardContent(pageRequest, boardContent);
    }

    // 게시글 제목 + 내용 검색
    public List<ResponseBoard> findAllByBoardTitleOrBoardContent(String boardTitle, String boardContent) {
        return boardRepository.findAllByBoardTitleOrBoardContent(pageRequest, boardTitle, boardContent);
    }
}
