package com.example.iworks.domain.schedule.dto;

import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.global.model.entity.Code;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class ScheduleAssignResponseDto {

   private Integer scheduleId; // 할 일 아이디
   private Code scheduleDivisionId; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)
   private String scheduleTitle = "scheduleName"; //할 일 이름
   private LocalDateTime scheduleStartDate = LocalDateTime.now(); //할 일의 시작일시
   private LocalDateTime scheduleEndDate; //할 일의 종료일시
   private List<ScheduleAssign> scheduleAssigns = new ArrayList<>(); //할 일 배정자

   @QueryProjection
   public ScheduleAssignResponseDto(Integer scheduleId, Code scheduleDivisionId, String scheduleTitle, LocalDateTime scheduleStartDate, LocalDateTime scheduleEndDate) {
      this.scheduleId = scheduleId;
      this.scheduleDivisionId = scheduleDivisionId;
      this.scheduleTitle = scheduleTitle;
      this.scheduleStartDate = scheduleStartDate;
      this.scheduleEndDate = scheduleEndDate;
   }
   @QueryProjection
   public ScheduleAssignResponseDto(Integer scheduleId, Code scheduleDivisionId, String scheduleTitle, LocalDateTime scheduleStartDate, LocalDateTime scheduleEndDate, List<ScheduleAssign> scheduleAssigns) {
      this.scheduleId = scheduleId;
      this.scheduleDivisionId = scheduleDivisionId;
      this.scheduleTitle = scheduleTitle;
      this.scheduleStartDate = scheduleStartDate;
      this.scheduleEndDate = scheduleEndDate;
      this.scheduleAssigns = scheduleAssigns;
   }
}
