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
        //DB에서 동일한 세션 ID가 있는지 확인.
        do{
            randomStringUtil.getRandomId(10);
        }while (isUnique(sessionId));
        // 없으면 리턴
        return sessionId;
    }

    public boolean isUnique(String target){
        Meeting meeting = meetingRepository.findMeetingByMeetingSessionId(target);
        return meeting == null;
    }

}
