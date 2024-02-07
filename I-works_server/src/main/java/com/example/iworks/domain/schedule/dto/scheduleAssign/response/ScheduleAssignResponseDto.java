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

   private Integer scheduleId; // 할 일 아이디
   private Integer scheduleAssigneeId; //할일 담당장 아이디
   private Integer scheduleAssigneeCategoryId; //할일 카테고리 아이디
   private String scheduleAssigneeCategoryName; //할일 카테고리 명
   private String scheduleDivisionName; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)
   private String scheduleTitle; //할 일 이름
   private LocalDateTime scheduleStartDate; //할 일의 시작일시
   private LocalDateTime scheduleEndDate; //할 일의 종료일시
//   private List<ScheduleAssign> scheduleAssigns; //할 일 배정자

   @QueryProjection
   public ScheduleAssignResponseDto(ScheduleAssign scheduleAssign) {
      this.scheduleAssigneeId = scheduleAssign.getScheduleAssigneeId();
      this.scheduleAssigneeCategoryId = scheduleAssign.getScheduleAssigneeCategory().getCodeId();
      this.scheduleAssigneeCategoryName = scheduleAssign.getScheduleAssigneeCategory().getCodeName();
      this.scheduleId = scheduleAssign.getSchedule().getScheduleId();
      this.scheduleDivisionName = scheduleAssign.getSchedule().getScheduleDivision().getCodeName();
      this.scheduleTitle = scheduleAssign.getSchedule().getScheduleTitle();
      this.scheduleStartDate = scheduleAssign.getSchedule().getScheduleStartDate();
      this.scheduleEndDate = scheduleAssign.getSchedule().getScheduleEndDate();
   }
}
