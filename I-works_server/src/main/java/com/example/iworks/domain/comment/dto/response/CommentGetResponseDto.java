package com.example.iworks.domain.comment.dto.response;

import com.example.iworks.domain.comment.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentGetResponseDto {

    private int commentId; //댓글 아이디
    private int boardId; //게시글 아이디
    private int commentCreatorId; //댓글 작성자 아이디
    private int parentCommentId; //부모 댓글 아이디
    private int commentDepth; //댓글 계층
    private String commentContent; //댓글 내용
    private LocalDateTime commentCreatedAt; //댓글 작성 일시
    private LocalDateTime commentUpdatedAt; //댓글 수정 일시

    public CommentGetResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.boardId = comment.getBoardId();
        this.commentCreatorId = comment.getCommentCreatorId();
        this.parentCommentId = comment.getParentCommentId();
        this.commentDepth = comment.getCommentDepth();
        this.commentContent = comment.getCommentContent();
        this.commentCreatedAt = comment.getCommentCreatedAt();
        this.commentUpdatedAt = comment.getCommentUpdatedAt();
    }

}
