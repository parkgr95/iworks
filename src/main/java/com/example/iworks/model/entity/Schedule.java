package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Getter
@IdClass(ScheduleId.class)
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private int scheduleId; // 할 일 아이디

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_category_code_id",
            referencedColumnName = "code_id",
            nullable = false, updatable = false, insertable = false
    )
    private Code scheduleCategoryCode; //할 일 카테코리 코드

    @Id
    @Column(name = "schedule_owner_id",
            nullable = false, updatable = false, insertable = false
    )
    private int scheduleOwnerId; // 할 일 주체 아이디

    @Column(name = "schedule_title", nullable = false)
    private String scheduleTitle; //할 일 이름

    @Column(name = "schedule_priority", nullable = false)
    private String schedulePriority; //할 일 우선순위 H: high, M:Medium, L:low

    @Column(name = "schedule_content")
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

    @Column(name = "schedule_place")
    private String schedulePlace; //할 일의 장소

    @Column(name = "schedule_creator_id", nullable = false)
    private int scheduleCreatorId; // 등록자 아이디

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_created_at", nullable = false)
    private LocalDateTime scheduleCreatedAt; //등록 일시

    @Column(name = "schedule_modifier_id")
    private int scheduleModifierId; // 수정한 사람의 아이디

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_modified_at")
    private LocalDateTime scheduleModifiedAt; //할 일의 수정일시

    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;


}
