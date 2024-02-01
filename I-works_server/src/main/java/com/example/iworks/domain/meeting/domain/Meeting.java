package com.example.iworks.domain.meeting.domain;

import com.example.iworks.domain.schedule.domain.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id", nullable = false)
    private int meetingId; // 회의방 아이디

    @OneToOne(mappedBy = "scheduleMeeting", fetch = FetchType.LAZY)
    private Schedule schedule; // 할 일 아이디(외래키)

    @Column(name = "meeting_date")
    private LocalDateTime meetingDate; // 회의 일시

    @Column(name = "meeting_code", length = 2000)
    private String meetingCode; // 회의 참여 코드

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        if (schedule.getScheduleMeeting() != this){
            schedule.setMeeting(this);
        }
    }
    public void updateMeeting(LocalDateTime meetingDate, String meetingCode){
        this.meetingDate = meetingDate;
        this.meetingCode = meetingCode;
    }
}
