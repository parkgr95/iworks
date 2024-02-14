package com.example.iworks.domain.meeting.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface MeetingService {
    ResponseEntity<Map<String, Object>> getSessionUrlByMeetingId(int meetingId);

}
