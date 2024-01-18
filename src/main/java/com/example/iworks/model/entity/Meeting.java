package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@Getter
@Setter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id", nullable = false)
    private int id; // 회의방 아이디

    @Column(name = "meeting_date")
    private LocalDateTime meetingDate; // 회의 일시

    @Column(name = "meeting_code", length = 2000)
    private String meetingCode; // 회의 참여 코드


}
