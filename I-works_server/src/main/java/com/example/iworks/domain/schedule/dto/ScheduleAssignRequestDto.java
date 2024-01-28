package com.example.iworks.domain.schedule.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//@NoArgsConstructor
@Getter
@Builder
public class ScheduleAssignRequestDto {

   private int scheduleCategoryCodeId;
   private int scheduleAssigneeId;

   @QueryProjection
   public ScheduleAssignRequestDto(int scheduleCategoryCodeId, int scheduleAssigneeId) {
      this.scheduleCategoryCodeId = scheduleCategoryCodeId;
      this.scheduleAssigneeId = scheduleAssigneeId;
   }
}
