package com.example.iworks.domain.meeting.service;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.meeting.repository.MeetingRepository;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{
    private final MeetingRepository meetingRepository;
    private final Response response;
    @Value("${openvidu.host}")
    private String url;

    @Override
    public ResponseEntity<Map<String, Object>> getSessionUrlByMeetingId(int meetingId) {
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        if(meeting == null){
            return response.handleFail("존재하지 않는 세션입니다.",null);
        }

        return response.handleSuccess(url+"/#/"+meeting.getMeetingSessionId());
    }
}
