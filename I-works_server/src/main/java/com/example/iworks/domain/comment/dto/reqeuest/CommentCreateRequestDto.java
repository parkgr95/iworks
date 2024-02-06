package com.example.iworks.domain.comment.dto.reqeuest;

import com.example.iworks.domain.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CommentCreateRequestDto {

    private int boardId; //게시글 아이디
    private int commentCreatorId; //댓글 작성자 아이디
    private int parentCommentId; //부모 댓글 아이디
    private int commentDepth; //댓글 계층
    private String commentContent; //댓글 내용
    private LocalDateTime commentCreatedAt; //댓글 작성 일시

    public Comment toEntity() {
        return Comment.builder()
                .boardId(boardId)
                .commentCreatorId(commentCreatorId)
                .parentCommentId(parentCommentId)
                .commentDepth(commentDepth)
                .commentContent(commentContent)
                .commentCreatedAt(LocalDateTime.now())
                .build();
    }
}
