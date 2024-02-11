package com.example.iworks.domain.schedule.dto.schedule.request;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeBelong;
import com.example.iworks.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleCreateRequestDto {

    private int scheduleDivisionCodeId; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)
    private String scheduleTitle; //할 일 이름
    private Character schedulePriority ; //할 일 우선순위 H: high, M:Medium, L:low
    private String scheduleContent; //할 일 내용
    private LocalDateTime scheduleStartDate; //할 일의 시작일시
    private LocalDateTime scheduleEndDate; //할 일의 종료일시
    private String schedulePlace; //할 일의 장소
    private LocalDateTime meetingDate; // 회의 일시
    private List<AssigneeBelong> assigneeBelongs; // 담당자 카테고리 아이디, 담당자 아이디

    public Schedule toScheduleEntity(Code division, Meeting meeting, User creator){
        return Schedule.builder()
                .scheduleDivision(division)
                .scheduleTitle(this.scheduleTitle)
                .schedulePriority(this.schedulePriority)
                .scheduleContent(this.scheduleContent)
                .scheduleStartDate(this.scheduleStartDate)
                .scheduleEndDate(this.scheduleEndDate)
                .schedulePlace(this.schedulePlace)
                .scheduleMeeting(meeting)
                .scheduleCreator(creator)
                .build();
    }

    public Meeting toMeetingEntity(){
        return Meeting.builder()
                .meetingCode("sample Code")
                .meetingDate(this.meetingDate)
                .build();
    }

}
