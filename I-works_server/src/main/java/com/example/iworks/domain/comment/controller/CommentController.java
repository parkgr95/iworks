package com.example.iworks.domain.comment.controller;

import com.example.iworks.domain.comment.dto.reqeuest.CommentCreateRequestDto;
import com.example.iworks.domain.comment.dto.reqeuest.CommentUpdateRequestDto;
import com.example.iworks.domain.comment.service.CommentService;
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
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final JwtProvider jwtProvider;
    private final Response response;

    //게시글 별 댓글 전체 조회
    @GetMapping("/byBoard/{boardId}")
    public ResponseEntity<?> getCommentsByBoard(@PathVariable(name = "boardId") int boardId) {
        return response.handleSuccess(commentService.getAllByBoard(boardId));
    }

    //댓글 등록
    @PostMapping
    public ResponseEntity<?> createComment(@Validated @RequestHeader("Authorization") String authorizationToken,
                                           @RequestBody CommentCreateRequestDto commentCreateRequestDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error : {}", bindingResult);
            return response.handleFail("댓글 등록 실패", getErrorMessages(bindingResult));
        }
        int userId = findUserId(authorizationToken);
        commentService.createComment(userId, commentCreateRequestDto);
        return response.handleSuccess("댓글 등록 완료");
    }

    //댓글 수정
    @PutMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "commentId") int commentId,
                                           @RequestHeader("Authorization") String authorizationToken,
                                           @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        int userId = findUserId(authorizationToken);
        commentService.updateComment(commentId, userId, commentUpdateRequestDto);
        return response.handleSuccess("댓글 수정 완료");
    }

    //댓글 삭제
    @PutMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "commentId") int commentId,
                                           @RequestHeader("Authorization") String authorizationToken) {
        int userId = findUserId(authorizationToken);
        commentService.deleteComment(commentId, userId);
        return response.handleSuccess("댓글 삭제 완료");
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
