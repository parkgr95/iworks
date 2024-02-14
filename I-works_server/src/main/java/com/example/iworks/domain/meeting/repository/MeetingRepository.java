package com.example.iworks.domain.meeting.repository;

import com.example.iworks.domain.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Meeting findMeetingByMeetingSessionId(String sessionId);
    Meeting findMeetingByMeetingId(int meetingId);
}
