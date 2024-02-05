package com.example.iworks.global.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchConditionDate {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
