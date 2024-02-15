package com.example.iworks.domain.schedule.dto.schedule.response;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final int scheduleId;
    private final String scheduleDivisionName;
    private final String scheduleTitle;
    private final Character schedulePriority;
    private final String scheduleContent;
    private final LocalDateTime scheduleStartDate;
    private final LocalDateTime scheduleEndDate;
    private final Boolean scheduleIsFinish;
    private final LocalDateTime scheduleFinishedAt;
    private final String schedulePlace;

    private LocalDateTime meetingDate; // 회의 일시
    private String meetingSessionId; // 회의 참여 세션 아이디

    private Integer scheduleCreatorId;
    private String scheduleCreatorName;

    private final LocalDateTime scheduleCreatedAt;
    private Integer scheduleModifierId;
    private String scheduleModifierName;
    private final LocalDateTime scheduleModifiedAt;


    public ScheduleResponseDto(Schedule schedule){
        this.scheduleId = schedule.getScheduleId();
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
            this.meetingSessionId = scheduleMeeting.getMeetingSessionId();
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




