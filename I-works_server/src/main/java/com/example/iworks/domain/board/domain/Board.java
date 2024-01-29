package com.example.iworks.domain.board.domain;

import com.example.iworks.global.model.entity.Code;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private int boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_category_code_id",
            referencedColumnName = "code_id",
            nullable = false, updatable = false, insertable = false
    )
    private Code boardCategoryCode; // 게시판 카테고리

    @Column(name = "board_owner_id",
            nullable = false, updatable = false, insertable = false )
    private int boardOwnerId; // 게시판 주체 아이디

    @Column(name = "board_creator_id", nullable = false)
    private int boardCreatorId; // 작성자 아이디

    @Column(name = "board_title", length = 30)
    private String boardTitle; // 제목

    @Column(name = "board_content", columnDefinition = "text")
    private String boardContent; // 내용

    @Column(name = "board_created_at")
    private LocalDateTime boardCreatedAt; // 작성일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "board_updated_at")
    private LocalDateTime boardUpdatedAt; // 수정일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "board_deleted_at")
    private LocalDateTime boardDeletedAt; // 삭제일시

    @Column(name = "board_is_deleted")
    private Boolean boardIsDeleted; // 삭제여부

    @Column(name = "board_modifier_id")
    private Integer boardModifierId; // 수정자 아이디

    public void updateBoard(Board board) {
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.boardUpdatedAt = LocalDateTime.now();
        this.boardModifierId = board.getBoardModifierId();
    }

}