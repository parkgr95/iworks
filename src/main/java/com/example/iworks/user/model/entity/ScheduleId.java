package com.example.iworks.user.model.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ScheduleId implements Serializable {
    private int scheduleId; // 할 일 아이디
    private int scheduleCategoryCode; //할 일 카테코리 코드
    private int scheduleOwnerId; //할 일 주체 아이디

    public ScheduleId(int scheduleId, int scheduleCategoryCode, int scheduleOwnerId) {
        this.scheduleId = scheduleId;
        this.scheduleCategoryCode = scheduleCategoryCode;
        this.scheduleOwnerId = scheduleOwnerId;
    }

    public ScheduleId() {
    }
}
