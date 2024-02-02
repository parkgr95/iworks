package com.example.iworks.domain.schedule.dto.scheduleAssign;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

//@NoArgsConstructor
@Getter
@Builder
@ToString
public class ScheduleAssignSearchParameterDto {

   private int scheduleCategoryCodeId;
   private int scheduleAssigneeId;

   @QueryProjection
   public ScheduleAssignSearchParameterDto(int scheduleCategoryCodeId, int scheduleAssigneeId) {
      this.scheduleCategoryCodeId = scheduleCategoryCodeId;
      this.scheduleAssigneeId = scheduleAssigneeId;
   }
}
