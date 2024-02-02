package com.example.iworks.domain.schedule.dto.scheduleAssign;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

//@NoArgsConstructor
@Getter
@Builder
//@ToString
public class ScheduleAssignRequestDto {

    private int scheduleCategoryCodeId;
    private int scheduleAssigneeId;

    @QueryProjection
    public ScheduleAssignRequestDto(int scheduleCategoryCodeId, int scheduleAssigneeId) {
        this.scheduleCategoryCodeId = scheduleCategoryCodeId;
        this.scheduleAssigneeId = scheduleAssigneeId;
    }
}

