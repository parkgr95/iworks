package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.admin.service.adminBoard.AdminBoardService;
import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.service.BoardService;
import com.example.iworks.global.util.JwtProvider;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/board")
public class AdminBoardController {

    private final BoardService boardService;
    private final AdminBoardService adminBoardService;
    private final JwtProvider jwtProvider;
    private final Response response;

    //게시글 등록
    @PostMapping
    public ResponseEntity<?> createBoard(@Validated @RequestHeader("Authorization") String authorizationToken,
                                         @RequestBody BoardCreateRequestDto boardCreateRequestDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error : {}", bindingResult);
            return response.handleFail("게시글 등록 실패", getErrorMessages(bindingResult));
        }
        int userId = findUserId(authorizationToken);
        boardService.createBoard(userId, boardCreateRequestDto);
        return response.handleSuccess("게시글 등록 완료");
    }

    //게시글 수정
    @PostMapping("/update/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable(name = "boardId") int boardId,
                                         @RequestHeader("Authorization") String authorizationToken,
                                         @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        int userId = findUserId(authorizationToken);
        adminBoardService.updateBoard(boardId, userId, boardUpdateRequestDto);
        return response.handleSuccess("게시글 수정 완료");
    }

    //게시글 삭제
    @PostMapping("/delete/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "boardId") int boardId,
                                         @RequestHeader("Authorization") String authorizationToken) {
        int userId = findUserId(authorizationToken);
        adminBoardService.deleteBoard(boardId, userId);
        return response.handleSuccess("게시글 삭제 완료");
    }

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity<?> getBoards() {
        return response.handleSuccess(boardService.getAll());
    }

    //게시글 세부 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable(name = "boardId") int boardId) {
        return response.handleSuccess(boardService.getBoard(boardId));
    }

    //카테고리 별 게시글 전체 조회
    @GetMapping("/byCategory")
    public ResponseEntity<?> getBoardsByCategory(
            @RequestParam(name = "boardCategoryCodeId") int boardCategoryCodeId,
            @RequestParam(name = "boardOwnerId") int boardOwnerId) {
        return response.handleSuccess(boardService.getAllByCategory(boardCategoryCodeId, boardOwnerId));
    }

    //카테고리 별 게시글 세부 조회
    @GetMapping("/byCategory/{boardId}")
    public ResponseEntity<?> getBoardByCategory(
            @PathVariable(name = "boardId") int boardId,
            @RequestParam(name = "boardCategoryCodeId") int boardCategoryCodeId,
            @RequestParam(name = "boardOwnerId") int boardOwnerId) {
        return response.handleSuccess(boardService.getByCategory(boardId, boardCategoryCodeId, boardOwnerId));
    }

    //작성한 게시글 전체 조회
    @GetMapping("/byCreator")
    public ResponseEntity<?> getBoardsByCreator(@RequestHeader("Authorization") String authorizationToken) {
        int userId = findUserId(authorizationToken);
        return response.handleSuccess(boardService.getAllByCreator(userId));
    }

    //키워드 별 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<?> getBoardsByKeyword(@RequestBody BoardSearchRequestDto keyword) {
        return response.handleSuccess(boardService.getAllByKeyword(keyword));
    }

    //통합 키워드 별 게시글 검색
    @GetMapping("/total-search")
    public ResponseEntity<?> getBoardsByKeywords(@RequestParam(name = "keywords") String keywords) {
        return response.handleSuccess(boardService.getAllByKeywords(keywords));
    }

    private static List<String> getErrorMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
    }

    private int findUserId(String authorizationToken) {
        return jwtProvider.getUserId(authorizationToken);
    }

}
