package com.example.iworks.domain.schedule.dto.schedule.request;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleCreateRequestDto {

    @NotNull
    private int scheduleDivisionCodeId; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)

    @NotNull
    private String scheduleTitle; //할 일 이름

    @NotNull
    private Character schedulePriority ; //할 일 우선순위 H: high, M:Medium, L:low

    @NotBlank
    private String scheduleContent; //할 일 내용

    @NotNull
    private LocalDateTime scheduleStartDate; //할 일의 시작일시

    @NotNull
    private LocalDateTime scheduleEndDate; //할 일의 종료일시

    @NotBlank
    private String schedulePlace; //할 일의 장소

    @NotNull
    private Boolean isCreateMeeting; // 회의 생성 여부

    private LocalDateTime meetingDate; // 회의 일시

    @NotNull
    private List<AssigneeInfo> assigneeInfos; // 담당자 카테고리 아이디, 담당자 아이디

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

    public Meeting toMeetingEntity(String sessionId){
        return Meeting.builder()
                .meetingSessionId(sessionId)
                .meetingDate(this.meetingDate)
                .build();
    }

}
