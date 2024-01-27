package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.entity.Code;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void 할일저장(){
        //given
        User user = new User();
        user.setUserId(1);
        userRepository.save(user);
        System.out.println("user 준비 데이터 : " + userRepository.findById(1));

        Schedule schedule = Schedule.builder()
                .scheduleId(1)
                .scheduleDivisionId(new Code())
                .scheduleTitle("title")
                .schedulePriority('H')
                .scheduleContent("sdfdf")
                .scheduleStartDate(LocalDateTime.now())
                .scheduleEndDate(LocalDateTime.now())
                .scheduleDeadline(LocalDateTime.now())
                .schedulePlace("공원")
//                .scheduleMeeting(new Meeting())
                .scheduleCreator(user)
//                .scheduleCreatedAt(LocalDateTime.now())
//                .scheduleModifier(new User())
//                .scheduleModifiedAt(LocalDateTime.now())
//                .scheduleAssigns(new ArrayList<>())
                .build();

        //when
        Schedule savedSchedule = scheduleRepository.save(schedule);
        Schedule foundSchedule = scheduleRepository.findById(schedule.getScheduleId()).get();
        System.out.println("저장한 할 일 "+ savedSchedule);
        System.out.println("조회한 할 일 "+foundSchedule);

        //then
        Assertions.assertEquals(foundSchedule.getScheduleId(), savedSchedule.getScheduleId());
        Assertions.assertEquals(foundSchedule.getScheduleTitle(), savedSchedule.getScheduleTitle());
        Assertions.assertEquals(foundSchedule.getSchedulePriority(), savedSchedule.getSchedulePriority());

    }

}