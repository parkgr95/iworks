package com.example.iworks.domain.schedule.dto.scheduleAssign.response;

import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class ScheduleAssignResponseDto {

   private final Integer scheduleId; // 할 일 아이디
   private final Integer scheduleAssigneeId; //할일 담당장 아이디
   private final Integer scheduleAssigneeCategoryId; //할일 카테고리 아이디
   private final String scheduleAssigneeCategoryName; //할일 카테고리 명

   private final String scheduleDivisionName; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)
   private final String scheduleTitle; //할 일 이름
   private final Character schedulePriority; //할일 우선순위
   private final LocalDateTime scheduleStartDate; //할 일의 시작일시
   private final LocalDateTime scheduleEndDate; //할 일의 종료일시
   private final Boolean scheduleIsFinish; //할 일 완료 여부
   private final LocalDateTime scheduleFinishedAt; //할 일 완료 일시

   @QueryProjection
   public ScheduleAssignResponseDto(ScheduleAssign scheduleAssign) {
      this.scheduleAssigneeId = scheduleAssign.getScheduleAssigneeId();
      this.scheduleAssigneeCategoryId = scheduleAssign.getScheduleAssigneeCategory().getCodeId();
      this.scheduleAssigneeCategoryName = scheduleAssign.getScheduleAssigneeCategory().getCodeName();
      this.scheduleId = scheduleAssign.getSchedule().getScheduleId();
      this.scheduleDivisionName = scheduleAssign.getSchedule().getScheduleDivision().getCodeName();
      this.scheduleTitle = scheduleAssign.getSchedule().getScheduleTitle();
      this.schedulePriority = scheduleAssign.getSchedule().getSchedulePriority();
      this.scheduleStartDate = scheduleAssign.getSchedule().getScheduleStartDate();
      this.scheduleEndDate = scheduleAssign.getSchedule().getScheduleEndDate();
      this.scheduleIsFinish = scheduleAssign.getSchedule().getScheduleIsFinish();
      this.scheduleFinishedAt = scheduleAssign.getSchedule().getScheduleFinishedAt();
   }
}
