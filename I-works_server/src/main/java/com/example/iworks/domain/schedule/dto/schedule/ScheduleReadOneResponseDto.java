package com.example.iworks.domain.schedule.dto.schedule;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.meeting.dto.MeetingDto;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.model.entity.CodeDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class ScheduleReadOneResponseDto {

    private String scheduleDivisionName;
    private String scheduleTitle;
    private Character schedulePriority;
    private String scheduleContent;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;
    private Boolean scheduleIsFinish;
    private LocalDateTime scheduleFinishedAt;
    private String schedulePlace;

    private LocalDateTime meetingDate; // 회의 일시
    private String meetingCode; // 회의 참여 코드

    private Integer scheduleCreatorId;
    private String scheduleCreatorName;

    private LocalDateTime scheduleCreatedAt;
    private Integer scheduleModifierId;
    private String scheduleModifierName;
    private LocalDateTime scheduleModifiedAt;

    public ScheduleReadOneResponseDto(Schedule schedule){
        this.scheduleDivisionName = schedule.getScheduleDivision().getCodeName();
        this.scheduleTitle = schedule.getScheduleTitle();
        this.schedulePriority = schedule.getSchedulePriority();
        this.scheduleContent = schedule.getScheduleContent();
        this.scheduleStartDate = schedule.getScheduleStartDate();
        this.scheduleEndDate = schedule.getScheduleEndDate();
        this.scheduleIsFinish = schedule.getScheduleIsFinish();
        this.scheduleFinishedAt = schedule.getScheduleFinishedAt();
        this.schedulePlace = schedule.getSchedulePlace();

        Meeting scheduleMeeting = schedule.getScheduleMeeting();
        if (scheduleMeeting != null) {
            this.meetingDate = scheduleMeeting.getMeetingDate();
            this.meetingCode = scheduleMeeting.getMeetingCode();
        }

        User scheduleCreator = schedule.getScheduleCreator();
        if (scheduleCreator != null) {
            this.scheduleCreatorId = scheduleCreator.getUserId();
            this.scheduleCreatorName = scheduleCreator.getUserName() ;
        }

        this.scheduleCreatedAt = schedule.getScheduleCreatedAt();

        User scheduleModifier = schedule.getScheduleModifier();
        if (scheduleModifier != null) {
            this.scheduleModifierId = scheduleModifier.getUserId();
            this.scheduleModifierName = scheduleModifier.getUserName();
        }

        this.scheduleModifiedAt = schedule.getScheduleModifiedAt();

    }

}




