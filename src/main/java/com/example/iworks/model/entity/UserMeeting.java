package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_meeting")
@Getter
@Setter
public class UserMeeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_meeting_id", nullable = false)
    private int id; // 유저-회의 식별 아이디

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting; // 회의방 아이디 (외래키)

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 식별 아이디 (외래키)

    @Column(name = "is_enter", nullable = false)
    private boolean isEnter; // 참여 상태 : 참여중 1, 미참여 0

    @Column(name = "user_meeting_at")
    private LocalDateTime userMeetingAt; // 회의 참여 일시

}
