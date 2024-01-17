package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private int id; // 글 아이디

    @Column(name = "board_creator_id", nullable = false)
    private int creatorId; // 작성자 아이디

    @Column(name = "board_title", length = 30)
    private String title; // 제목

    @Column(name = "board_content", columnDefinition = "text")
    private String content; // 내용

    @Column(name = "board_created_at")
    private String createdAt; // 작성일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "board_updated_at")
    private LocalDateTime updatedAt; // 수정일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "board_deleted_at")
    private LocalDateTime deletedAt; // 삭제일시

    @Column(name = "board_is_deleted")
    private boolean isDeleted; // 삭제여부

    @Column(name = "board_modifier_id")
    private Integer modifierId; // 수정자 아이디

    // 카테고리 고드 아이디
    // 게시판 주체 아이디

}
