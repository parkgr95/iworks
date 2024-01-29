package com.example.iworks.domain.board.controller;

import com.example.iworks.domain.board.dto.request.RequestBoard;
import com.example.iworks.domain.board.dto.request.SearchKeyword;
import com.example.iworks.domain.board.service.BoardService;
import com.example.iworks.global.model.entity.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 목록 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> findBoards() {
        return ResponseEntity.ok(boardService.findBoards());
    }

    // 게시판 카테고리별 게시글 조회 (공지, 자유)
    @GetMapping("/byCategory")
    public ResponseEntity<?> findAllByBoardCategoryCode(@RequestParam Code categoryCode) {
        return ResponseEntity.ok(boardService.findAllByBoardCategoryCode(categoryCode));
    }

    // 게시판 카테고리별 게시글 조회 (부서, 팀)
    @GetMapping("byCategoryAndOwner")
    public ResponseEntity<?> findAllByBoardCategoryCodeAndBoardOwnerId(
            @RequestParam Code categoryCode,
            @RequestParam int boardOwnerId) {
        return ResponseEntity.ok(boardService.findAllByBoardCategoryCodeAndBoardOwnerId(categoryCode, boardOwnerId));
    }

    //게시글 작성
    @PostMapping("/")
    public ResponseEntity<?> saveBoard(@RequestBody RequestBoard requestBoard) {
        boardService.saveBoard(requestBoard.toEntity());
        return ResponseEntity.ok().build();
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable int boardId, @RequestParam RequestBoard requestBoard) {
        boardService.updateBoard(boardId, requestBoard);
        return ResponseEntity.ok().build();
    }

    //게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable int boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchBoard(@RequestBody SearchKeyword keyword) {
        return ResponseEntity.ok(boardService.findAllByKeyword(keyword));
    }
}
