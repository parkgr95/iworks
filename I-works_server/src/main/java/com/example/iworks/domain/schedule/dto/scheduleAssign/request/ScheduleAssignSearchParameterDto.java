package com.example.iworks.domain.schedule.dto.scheduleAssign.request;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
//@ToString
public class ScheduleAssignSearchParameterDto {

   private int scheduleCategoryCodeId;
   private int scheduleAssigneeId;

   @QueryProjection
   public ScheduleAssignSearchParameterDto(int scheduleCategoryCodeId, int scheduleAssigneeId) {
      this.scheduleCategoryCodeId = scheduleCategoryCodeId;
      this.scheduleAssigneeId = scheduleAssigneeId;
   }
}
