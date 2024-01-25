package com.example.iworks.schedule.meeting.model.entity;

import com.example.iworks.user.model.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_meeting")
@Getter
@IdClass(UserMeetingId.class)
public class UserMeeting {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting; // 회의방 아이디 (외래키)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 식별 아이디 (외래키)

    @Column(name = "is_enter", nullable = false)
    private boolean isEnter; // 참여 상태 : 참여중 1, 미참여 0

    @Column(name = "user_meeting_at")
    private LocalDateTime userMeetingAt; // 회의 참여 일시

}
