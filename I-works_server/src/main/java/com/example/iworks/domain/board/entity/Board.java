package com.example.iworks.domain.board.entity;

import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.code.entity.Code;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "board")
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private int boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_category_code_id",
            referencedColumnName = "code_id",
            nullable = false, updatable = false
    )
    private Code boardCategoryCode; //게시판 카테고리

    @Column(name = "board_owner_id",
            nullable = false, updatable = false
    )
    private int boardOwnerId; //게시판 주체 아이디

    @Column(name = "board_creator_id", nullable = false)
    private int boardCreatorId; //게시글 작성자 아이디

    @Column(name = "board_title", length = 30)
    private String boardTitle; //게시글 제목

    @Column(name = "board_content", columnDefinition = "text")
    private String boardContent; //게시글 내용

    @JsonIgnore
    @Column(name = "board_modifier_id")
    private int boardModifierId; //게시글 수정자 아이디

    @Column(name = "board_created_at")
    private LocalDateTime boardCreatedAt; //게시글 작성 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "board_updated_at")
    private LocalDateTime boardUpdatedAt; //게시글 수정 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "board_deleted_at")
    private LocalDateTime boardDeletedAt; //게시글 삭제 일시

    @Column(name = "board_is_deleted", columnDefinition = "boolean default false")
    private Boolean boardIsDeleted; //게시글 삭제 여부

    public void update(BoardUpdateRequestDto requestBoard) {
        this.boardModifierId = requestBoard.getBoardModifierId();
        if (requestBoard.getBoardTitle() != null && !requestBoard.getBoardTitle().isEmpty()) {
            this.boardTitle = requestBoard.getBoardTitle();
        }
        if (requestBoard.getBoardContent() != null && !requestBoard.getBoardContent().isEmpty()) {
            this.boardContent = requestBoard.getBoardContent();
        }
        this.boardUpdatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.boardDeletedAt = LocalDateTime.now();
        this.boardIsDeleted = true;
    }

}