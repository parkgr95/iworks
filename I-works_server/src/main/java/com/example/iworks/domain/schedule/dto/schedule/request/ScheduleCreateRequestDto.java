package com.example.iworks.domain.schedule.dto.schedule.request;

import com.example.iworks.domain.meeting.domain.Meeting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreateRequestDto {

    private int scheduleId;
    private int scheduleDivisionCodeId; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)
    private String scheduleTitle; //할 일 이름
    private Character schedulePriority ; //할 일 우선순위 H: high, M:Medium, L:low
    private String scheduleContent; //할 일 내용
    private LocalDateTime scheduleStartDate; //할 일의 시작일시
    private LocalDateTime scheduleEndDate; //할 일의 종료일시
    private String schedulePlace; //할 일의 장소
    private LocalDateTime meetingDate; // 회의 일시
    private int scheduleCreatorId; // 등록자 아이디

}
