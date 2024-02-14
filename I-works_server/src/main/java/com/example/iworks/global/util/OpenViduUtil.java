package com.example.iworks.global.util;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenViduUtil {

    private final MeetingRepository meetingRepository;
    private final RandomStringUtil randomStringUtil;

    public String createSessionId(){
        String sessionId = null;
        do{
            sessionId = randomStringUtil.getRandomId(10);
        }while (!isUnique(sessionId));
        return sessionId;
    }

    public boolean isUnique(String target){
        Meeting meeting = meetingRepository.findMeetingByMeetingSessionId(target);
        return meeting == null;
    }

}
