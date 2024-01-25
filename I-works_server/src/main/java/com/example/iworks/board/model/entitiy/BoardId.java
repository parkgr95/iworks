package com.example.iworks.board.model.entitiy;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class BoardId implements Serializable {
    private int boardCategoryCode;
    private int boardOwnerId;
    private int boardId;
}
