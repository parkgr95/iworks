package com.example.iworks.domain.schedule.domain;

import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.global.model.entity.Code;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedule_assign")
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class ScheduleAssign {

    @Id
    @GeneratedValue
    @Column(name = "schedule_assign_id")
    private int scheduleAssignId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void setSchedule(Schedule schedule){
        if (this.schedule != null){ //이미 연관 관계가 있었다면
            this.schedule.getScheduleAssigns().remove(this);
        }
        this.schedule = schedule;
        schedule.getScheduleAssigns().add(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_assignee_category_id", referencedColumnName = "code_id", nullable = false)
    private Code scheduleAssigneeCategory; // 할일 배정자 카테고리 아이디

    @Column(name = "schedule_assignee_id", nullable = false)
    private int scheduleAssigneeId; //유저아이디 or 그룹아이디 or 부서아이디  or 전체

}


