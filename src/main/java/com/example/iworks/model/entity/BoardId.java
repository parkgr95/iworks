package com.example.iworks.model.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class BoardId implements Serializable {
    private int boardCategoryCode;
    private int boardOwnerId;
    private int boardId;
}
