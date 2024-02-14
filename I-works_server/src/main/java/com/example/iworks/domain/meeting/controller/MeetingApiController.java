package com.example.iworks.domain.meeting.controller;

import com.example.iworks.domain.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingApiController {
    private final MeetingService meetingService;
    @GetMapping("/{meetingId}")
    public ResponseEntity<Map<String,Object>> getMeetingUrl(@PathVariable("meetingId") int meetingId) {
        return meetingService.getSessionUrlByMeetingId(meetingId);
    }

}
