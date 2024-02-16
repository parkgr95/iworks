package com.example.iworks.domain.schedule.domain;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId; // 할 일 아이디

    @ManyToOne(fetch = FetchType.LAZY) //단방향
    @JoinColumn(name = "schedule_division_id")
    private Code scheduleDivision; //할일 분류 아이디 , 행사 or 업무 or 개인일정(병가) or  개인일정(외출) or  개인일정(휴가)

    @Builder.Default
    @Column(name = "schedule_title", nullable = false, length = 50)
    private String scheduleTitle = "scheduleName"; //할 일 이름

    @Builder.Default
    @Column(name = "schedule_priority", nullable = false, length = 1)
    private Character schedulePriority = 'M'; //할 일 우선순위 H: high, M:Medium, L:low

    @Builder.Default
    @Column(name = "schedule_content", columnDefinition = "TEXT" )
    private String scheduleContent = "this is content..."; //할 일 내용
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_start_date", nullable = false)
    private LocalDateTime scheduleStartDate; //할 일의 시작일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_end_date", nullable = false)
    private LocalDateTime scheduleEndDate; //할 일의 종료일시

    @Builder.Default
    @Column(name = "schedule_is_finish", nullable = false)
    private Boolean scheduleIsFinish = false; //할일의 완료 여부

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_finished_at")
    private LocalDateTime scheduleFinishedAt; //할일 완료 일시

    @Column(name = "schedule_place", length = 50)
    private String schedulePlace; //할 일의 장소

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_meeting_id", referencedColumnName = "meeting_id")
    private Meeting scheduleMeeting; //회의방 아이디

    @ManyToOne(fetch = FetchType.LAZY) //단방향
    @JoinColumn(name = "schedule_creator_id", referencedColumnName = "user_id", nullable = false)
    private User scheduleCreator; // 등록자 아이디

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_created_at", nullable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime scheduleCreatedAt = LocalDateTime.now(); //등록 일시

    @ManyToOne(fetch = FetchType.LAZY) //단방향
    @JoinColumn(name = "schedule_modifier_id", referencedColumnName = "user_id")
    private User scheduleModifier; // 수정한 사람의 아이디

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_modified_at", columnDefinition = "datetime default current_timestamp")
    private LocalDateTime scheduleModifiedAt; //할일의 수정일시

    @Builder.Default
    @Column(name = "schedule_is_deleted", nullable = false)
    private Boolean scheduleIsDeleted = false; //할일 삭제여부

    @Builder.Default
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) //ScheduleAssign - Code 단방향
    private List<ScheduleAssign> scheduleAssigns = new ArrayList<>(); //할일 배정자

    public void addScheduleAssign(ScheduleAssign scheduleAssign){
        this.scheduleAssigns.add(scheduleAssign);
        scheduleAssign.setSchedule(this);
    }
    public void addScheduleAssignList(List<ScheduleAssign> scheduleAssignList){
        this.scheduleAssigns.addAll(scheduleAssignList);
        for (ScheduleAssign scheduleAssign : scheduleAssignList) {
            scheduleAssign.setSchedule(this);
        }
    }

    public void setMeeting(Meeting meeting){
        this.scheduleMeeting = meeting;
        if (meeting.getSchedule() != this){
            meeting.setSchedule(this);
        }
    }
    public void isFinished(boolean isFinish){
        this.scheduleIsFinish = isFinish;
        this.scheduleFinishedAt = LocalDateTime.now();

    }

    public void updateSchedule(Code code, ScheduleUpdateRequestDto scheduleUpdateRequestDto){
        this.scheduleDivision = code;
        this.scheduleTitle = scheduleUpdateRequestDto.getScheduleTitle();
        this.schedulePriority = scheduleUpdateRequestDto.getSchedulePriority();
        this.scheduleContent = scheduleUpdateRequestDto.getScheduleContent();
        this.scheduleStartDate = scheduleUpdateRequestDto.getScheduleStartDate();
        this.scheduleEndDate = scheduleUpdateRequestDto.getScheduleEndDate();
        this.schedulePlace = scheduleUpdateRequestDto.getSchedulePlace();
        if (scheduleMeeting != null) this.scheduleMeeting.updateMeeting(scheduleUpdateRequestDto.getMeetingDate());
    }

    public void delete(){
        this.scheduleIsDeleted = true;
    }

}
