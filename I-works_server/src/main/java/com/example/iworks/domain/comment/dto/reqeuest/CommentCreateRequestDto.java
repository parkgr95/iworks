package com.example.iworks.domain.comment.dto.reqeuest;

import com.example.iworks.domain.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CommentCreateRequestDto {

    private Integer boardId; //게시글 아이디
    private Integer parentCommentId; //부모 댓글 아이디
    private Integer commentDepth; //댓글 계층
    private String commentContent; //댓글 내용
    private LocalDateTime commentCreatedAt; //댓글 작성 일시

    public Comment toEntity(Integer userId) {
        return Comment.builder()
                .boardId(boardId)
                .commentCreatorId(userId)
                .parentCommentId(parentCommentId)
                .commentDepth(commentDepth)
                .commentContent(commentContent)
                .commentCreatedAt(LocalDateTime.now())
                .build();
    }
}
