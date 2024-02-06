package com.example.iworks.domain.comment.domain;

import com.example.iworks.domain.comment.dto.reqeuest.CommentUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id", nullable = false)
    private int commentId;

    @JoinColumn(name = "board_id", nullable = false)
    private int boardId; //게시글 아이디

    @Column(name = "comment_creator_id", nullable = false)
    private int commentCreatorId; //댓글 작성자 아이디

    @Column(name="parent_comment_id", nullable = false)
    private int parentCommentId; //부모 댓글 아이디

    @Column(name = "comment_depth", nullable = false)
    private int commentDepth; //댓글 계층

    @Column(name = "comment_content")
    private String commentContent; //댓글 내용

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_created_at")
    private LocalDateTime commentCreatedAt; //댓글 작성 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_updated_at")
    private LocalDateTime commentUpdatedAt; //댓글 수정 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_deleted_at")
    private LocalDateTime commentDeletedAt; //댓글 삭제 일시

    @Column(name = "comment_is_deleted", columnDefinition = "boolean default false")
    private boolean commentIsDeleted; //댓글 삭제 여부

    public void update(CommentUpdateRequestDto commentUpdateRequestDto) {
        this.commentContent = commentUpdateRequestDto.getCommentContent();
        this.commentUpdatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.commentDeletedAt = LocalDateTime.now();
        this.commentIsDeleted = true;
    }

}
