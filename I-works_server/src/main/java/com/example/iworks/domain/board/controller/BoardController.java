package com.example.iworks.domain.board.controller;

import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.service.BoardService;
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
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final Response response;

    //게시글 등록
    @PostMapping("/")
    public ResponseEntity<?> createBoard(@Validated @RequestBody BoardCreateRequestDto boardCreateRequestDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error : {}", bindingResult);
            return response.handleFail("게시글 등록 실패", getErrorMessages(bindingResult));
        }
        boardService.createBoard(boardCreateRequestDto);
        return response.handleSuccess("게시글 등록 완료");
    }

    //게시글 수정
    @PutMapping("/update/{boardId}")
    public ResponseEntity<?> updateBoard(@Validated @PathVariable(name = "boardId") int boardId,
                                         @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error : {}", bindingResult);
            return response.handleFail("게시글 수정 실패", getErrorMessages(bindingResult));
        }
        boardService.updateBoard(boardId, boardUpdateRequestDto);
        return response.handleSuccess("게시글 수정 완료");
    }

    //게시글 삭제
    @PutMapping("/delete/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "boardId") int boardId) {
        boardService.deleteBoard(boardId);
        return response.handleSuccess("게시글 삭제 완료");
    }

    //게시글 전체 조회
    @GetMapping("/")
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
    public ResponseEntity<?> getBoardsByCreator(@RequestParam(name = "boardCreatorId") int boardCreatorId) {
        return response.handleSuccess(boardService.getAllByCreator(boardCreatorId));
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

    //북마크 등록/삭제
    @PostMapping("/bookmark/{boardId}")
    public ResponseEntity<?> updateBookmark(@PathVariable(name = "boardId") Integer boardId,
                                            @RequestParam(name = "userEid") String userEid) {
        Boolean isActivate = boardService.updateBookmark(boardId, userEid);
        if (Boolean.TRUE.equals(isActivate)) {
            return response.handleSuccess("북마크 등록 완료");
        }
        else {
            return response.handleSuccess("북마크 해제 완료");
        }
    }

    //북마크 된 게시글 전체 조회
    @GetMapping("/byBookmark")
    public ResponseEntity<?> getBookmarkedBoards(@RequestParam(name = "userEid") String userEid) {
        return response.handleSuccess(boardService.getAllByBookmark(userEid));
    }

    private static List<String> getErrorMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
    }

}
