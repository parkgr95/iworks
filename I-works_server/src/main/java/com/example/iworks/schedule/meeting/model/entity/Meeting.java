package com.example.iworks.schedule.meeting.model.entity;

import com.example.iworks.schedule.model.entitiy.Schedule;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@Getter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id", nullable = false)
    private int meetingId; // 회의방 아이디

    @OneToOne(mappedBy = "meeting", fetch = FetchType.LAZY)
    private Schedule schedule; // 할 일 아이디(외래키)

    @Column(name = "meeting_date")
    private LocalDateTime meetingDate; // 회의 일시

    @Column(name = "meeting_code", length = 2000)
    private String meetingCode; // 회의 참여 코드

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        if (schedule.getMeeting() != this){
            schedule.setMeeting(this);
        }

    }
}
