package com.example.iworks.domain.board.domain;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class BoardId implements Serializable {
    private int boardCategoryCode;
    private int boardOwnerId;
    private int boardId;
}
