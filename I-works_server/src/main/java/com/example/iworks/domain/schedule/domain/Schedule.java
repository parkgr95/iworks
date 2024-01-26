package com.example.iworks.domain.schedule.domain;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.model.entity.Code;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
public class Schedule {

    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Integer scheduleId; // 할 일 아이디

    @ManyToOne //단방향
    @JoinColumn(name = "schedule_division_id")
    private Code scheduleDivisionId; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)

    @Column(name = "schedule_title", nullable = false, length = 50)
    private String scheduleTitle; //할 일 이름

    @Column(name = "schedule_priority", nullable = false, length = 1)
    private Character schedulePriority; //할 일 우선순위 H: high, M:Medium, L:low

    @Column(name = "schedule_content", columnDefinition = "TEXT" )
    private String scheduleContent; //할 일 내용

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_start_date", nullable = false)
    private LocalDateTime scheduleStartDate; //할 일의 시작일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_end_date", nullable = false)
    private LocalDateTime scheduleEndDate; //할 일의 종료일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_deadline")
    private LocalDateTime scheduleDeadline; //할 일의 마감일시

    @Column(name = "schedule_place", length = 50)
    private String schedulePlace; //할 일의 장소

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting; //회의방 아이디

    public void setMeeting(Meeting meeting){
        this.meeting = meeting;
        if (meeting.getSchedule() != this){
            meeting.setSchedule(this);
        }
    }

    @ManyToOne //단방향
    @JoinColumn(name = "schedule_creator_id", referencedColumnName = "user_id", nullable = false)
    private User scheduleCreatorId; // 등록자 아이디

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_created_at", nullable = false)
    private LocalDateTime scheduleCreatedAt; //등록 일시

    @ManyToOne //단방향
    @JoinColumn(name = "schedule_modifier_id", referencedColumnName = "user_id", nullable = true)
    private User scheduleModifierId; // 수정한 사람의 아이디

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_modified_at")
    private LocalDateTime scheduleModifiedAt; //할 일의 수정일시

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) //ScheduleAssign - Code 단방향
    private List<ScheduleAssign> scheduleAssigns = new ArrayList<>(); //할 일 배정자

    public void addScheduleAssigns(ScheduleAssign scheduleAssign){
        this.scheduleAssigns.add(scheduleAssign);
        scheduleAssign.setSchedule(this);
    }

}
