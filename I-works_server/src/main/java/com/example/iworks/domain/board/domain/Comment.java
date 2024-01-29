package com.example.iworks.domain.board.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id", nullable = false)
    private int commentId; //댓글 아이디


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_board_id", referencedColumnName = "board_id", nullable = false, updatable = false, insertable = false)
    private Board board; //게시글

    @Column(name="parent_comment_id")
    private int parentCommentId; //부모 댓글 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id",
            referencedColumnName = "comment_id",
            updatable = false, insertable = false)
    private Comment parentComment; //부모 댓글

    @Column(name = "comment_creator_id", nullable = false)
    private int commentCreatorId; //댓글 작성자 아이디

    @Column(name = "comment_content")
    private String commentContent; //댓글 내용

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_created_at")
    private LocalDateTime commentCreatedAt; //댓글 작성일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_updated_at", nullable = false)
    private LocalDateTime commentUpdatedAt; //댓글 수정일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_deleted_at")
    private LocalDateTime commentDeletedAt; //댓글 삭제일시

    @Column(name = "comment_order", nullable = false)
    private int commentOrder; //댓글 순서

    @Column(name = "comment_depth", nullable = false)
    private int commentDepth; //계층

}
