package com.example.iworks.domain.schedule.dto.scheduleAssign.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
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
   public ScheduleAssignResponseDto(Integer scheduleId, String scheduleDivisionName, String scheduleTitle, LocalDateTime scheduleStartDate, LocalDateTime scheduleEndDate) {
      this.scheduleId = scheduleId;
      this.scheduleDivisionName = scheduleDivisionName;
      this.scheduleTitle = scheduleTitle;
      this.scheduleStartDate = scheduleStartDate;
      this.scheduleEndDate = scheduleEndDate;
   }

   @QueryProjection
   public ScheduleAssignResponseDto(Integer scheduleId, Integer scheduleAssigneeId, Integer scheduleAssigneeCategoryId, String scheduleAssigneeCategoryName, String scheduleDivisionName, String scheduleTitle, LocalDateTime scheduleStartDate, LocalDateTime scheduleEndDate) {
      this.scheduleId = scheduleId;
      this.scheduleAssigneeId = scheduleAssigneeId;
      this.scheduleAssigneeCategoryId = scheduleAssigneeCategoryId;
      this.scheduleAssigneeCategoryName = scheduleAssigneeCategoryName;
      this.scheduleDivisionName = scheduleDivisionName;
      this.scheduleTitle = scheduleTitle;
      this.scheduleStartDate = scheduleStartDate;
      this.scheduleEndDate = scheduleEndDate;
   }
}
