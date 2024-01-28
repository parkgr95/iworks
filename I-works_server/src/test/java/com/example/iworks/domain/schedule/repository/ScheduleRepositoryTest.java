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

        Schedule schedule = Schedule.builder()
                .scheduleId(1)
                .scheduleDivisionId(new Code())
                .scheduleTitle("title")
                .schedulePriority('H')
                .scheduleContent("sdfdf")
                .scheduleStartDate(LocalDateTime.now())
                .scheduleEndDate(LocalDateTime.now())
                .schedulePlace("공원")
                .scheduleCreator(user)
                .build();

        //when
        Schedule savedSchedule = scheduleRepository.save(schedule);
        Schedule foundSchedule = scheduleRepository.findById(schedule.getScheduleId()).get();

        //then
        Assertions.assertEquals(foundSchedule.getScheduleId(), savedSchedule.getScheduleId());
        Assertions.assertEquals(foundSchedule.getScheduleTitle(), savedSchedule.getScheduleTitle());
        Assertions.assertEquals(foundSchedule.getSchedulePriority(), savedSchedule.getSchedulePriority());

    }

//    @Test
    public void 할일수정(){
        //given
        User user = new User();
        user.setUserId(1);
        userRepository.save(user);

        Schedule schedule = Schedule.builder()
                .scheduleId(1)
                .scheduleDivisionId(new Code())
                .scheduleTitle("수정된 title")
                .schedulePriority('H')
                .scheduleContent("sdfdf")
                .scheduleStartDate(LocalDateTime.now())
                .scheduleEndDate(LocalDateTime.now())
                .schedulePlace("공원")
                .scheduleCreator(user)
                .build();

        scheduleRepository.save(schedule);
        //when
//        Schedule beforeSchedule = scheduleRepository.findById(1).get();
        Schedule alterSchedule = Schedule.builder()
                .scheduleId(1)
                .scheduleDivisionId(new Code())
                .scheduleTitle("수정된 title")
                .scheduleStartDate(LocalDateTime.now())
                .scheduleEndDate(LocalDateTime.now())
                .scheduleCreator(user)
                .build();
        Schedule alteredSchedule = scheduleRepository.save(alterSchedule);
        //then
        Assertions.assertEquals(alteredSchedule.getScheduleId(), schedule.getScheduleId());
        Assertions.assertEquals(alteredSchedule.getScheduleTitle(), "수정된 title");
        Assertions.assertEquals(alteredSchedule.getSchedulePlace(), schedule.getSchedulePlace());
    }

    @Test
    public void 기간별_할일목록_조회(){




    }

    @Test
    public void 유저의_기간별_캘린더목록_조회(){
        makeScheduleDummyData();
//        scheduleRepository.

    }

    @Test
    public void 선택된_할당자의_기간별_캘린더목록_조회(){
        //given


    }

    public void makeScheduleDummyData(){
        User user = new User();
        user.setUserId(1);
        userRepository.save(user);

        Schedule schedule = Schedule.builder()
                .scheduleDivisionId(new Code())
                .scheduleEndDate(LocalDateTime.now())
                .schedulePlace("회의실B")
                .scheduleCreator(user)
                .build();
        scheduleRepository.save(schedule);

        Schedule schedule2 = Schedule.builder()
                .scheduleDivisionId(new Code())
                .scheduleEndDate(LocalDateTime.now())
                .schedulePlace("공원")
                .scheduleCreator(user)
                .build();
        scheduleRepository.save(schedule2);

        Schedule schedule3 = Schedule.builder()
                .scheduleDivisionId(new Code())
                .scheduleEndDate(LocalDateTime.now())
                .schedulePlace("컨퍼런스룸")
                .scheduleCreator(user)
                .build();
        scheduleRepository.save(schedule3);
    }

    @Test
    public void 할일_조건_검색(){

    }

}