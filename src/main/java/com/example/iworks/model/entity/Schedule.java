package com.example.iworks.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private int id; // 할 일 아이디
    @Column(name = "schedule_title", nullable = false)
    private String title; //할 일 이름
    @Column(name = "schedule_priority", nullable = false)
    private String priority; //할 일 우선순위
    @Column(name = "schedule_content")
    private String content; //할 일 내용
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_start_date", nullable = false)
    private LocalDateTime startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_end_date", nullable = false)
    private LocalDateTime endDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_deadline")
    private LocalDateTime deadline; //마감 일시
    @Column(name = "schedule_place")
    private String place;
    @Column(name = "schedule_creator_id", nullable = false)
    private int creatorId; // 등록자 아이디
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_created_at", nullable = false)
    private LocalDateTime createdAt; //등록 일시
    @Column(name = "schedule_modifier_id")
    private int modifierId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_modified_at")
    private LocalDateTime modifiedAt;

}
