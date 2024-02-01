package com.example.iworks.domain.comment.controller;

import com.example.iworks.domain.comment.dto.reqeuest.CommentCreateRequestDto;
import com.example.iworks.domain.comment.dto.reqeuest.CommentUpdateRequestDto;
import com.example.iworks.domain.comment.service.CommentService;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final Response response;

    //게시글 별 댓글 전체 조회
    @GetMapping("/byBoard/{boardId}")
    public ResponseEntity<?> getCommentsByBoardId(@PathVariable(name = "boardId") int boardId) {
        return response.handleSuccess(commentService.getAllByBoardId(boardId));
    }

    //댓글 등록
    @PostMapping("/")
    public ResponseEntity<?> createComment(@RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        commentService.createComment(commentCreateRequestDto);
        return response.handleSuccess("댓글 등록 완료");
    }

    //댓글 수정
    @PutMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "commentId") int commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.updateComment(commentId, commentUpdateRequestDto);
        return response.handleSuccess("댓글 수정 완료");
    }

    //댓글 삭제
    @PutMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "commentId") int commentId) {
        commentService.deleteComment(commentId);
        return response.handleSuccess("댓글 삭제 완료");
    }

}
