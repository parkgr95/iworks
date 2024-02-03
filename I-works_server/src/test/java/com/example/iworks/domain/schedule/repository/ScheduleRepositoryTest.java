package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.response.ScheduleAssignResponseDto;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.schedule.repository.scheduleAssign.ScheduleAssignRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.entity.CodeGroup;
import com.example.iworks.global.model.repository.CodeGroupRepository;
import com.example.iworks.global.model.repository.CodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ScheduleAssignRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CodeGroupRepository codeGroupRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    ScheduleAssignRepository scheduleAssignRepository;


    @Test
    public void 할일저장(){
        //given
        User user = new User();
        userRepository.save(user);

        Schedule schedule = Schedule.builder()
                .scheduleId(1)
                .scheduleDivision(new Code())
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
        userRepository.save(user);

        Schedule schedule = Schedule.builder()
                .scheduleId(1)
                .scheduleDivision(new Code())
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
                .scheduleDivision(new Code())
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
    void 할일배정저장(){

        Department department = Department.builder()
                .departmentName("A부서").build();
        departmentRepository.save(department);

        User user = User.builder()
                .userDepartment(department)
                .build();
        userRepository.save(user);

        CodeGroup codeGroup = CodeGroup.builder()
                .codeGroupName("코드 그룹 1")
                .build();
        codeGroupRepository.save(codeGroup);

        Code categoryCode = Code.builder()
                .codeName("유저")
                .codeCodeGroup(codeGroup)
                .build();

        codeRepository.save(categoryCode);

        ScheduleAssign scheduleAssign = ScheduleAssign.builder()
                .scheduleAssigneeId(user.getUserId())
                .scheduleAssigneeCategory(categoryCode)
                .schedule(Schedule.builder().scheduleTitle("할 일1").build())
                .build();

        ScheduleAssign savedscheduleAssign = scheduleAssignRepository.save(scheduleAssign);
        assertEquals(user.getUserId(), savedscheduleAssign.getScheduleAssigneeId());
        assertEquals(categoryCode.getCodeName(), savedscheduleAssign.getScheduleAssigneeCategory().getCodeName());
        System.out.println("할일배정 저장");
        System.out.println(savedscheduleAssign);
    }

    @Test
    @Rollback(value = false)
    void 할일_배정_검색() {

        //필요한 데이터 입력
        departmentRepository.save(Department.builder()
                .departmentName("A부서")
                .departmentLeaderId(21)
                .build());
        Department department = departmentRepository.findByDepartmentName("A부서");

        userRepository.save(User.builder()
                .userEid("123456")
                .userNameFirst("user_ssafy")
                .userDepartment(department)
                .build());
        User user = userRepository.findByUserEid("123456");

        codeGroupRepository.save(CodeGroup.builder()
                .codeGroupName("코드 그룹 1")
                .build());
        CodeGroup codeGroup =codeGroupRepository.findByCodeGroupName("코드 그룹 1");

        codeRepository.save(Code.builder()
                .codeName("유저")
                .codeCodeGroup(codeGroup)
                .build());
        Code categoryCode = codeRepository.findCodeByCodeName("유저");

        codeRepository.save(Code.builder()
                .codeName("부서")
                .codeCodeGroup(codeGroup)
                .build());
        Code categoryCode2 = codeRepository.findCodeByCodeName("부서");

        codeRepository.save(Code.builder()
                .codeName("업무")
                .codeCodeGroup(codeGroup)
                .build());
        Code scheduleDivisionCode = codeRepository.findCodeByCodeName("업무");

        //할일 배정 : 유저에 배정

        scheduleRepository.save(Schedule.builder()
                .scheduleDivision(scheduleDivisionCode)
                .scheduleTitle("유저의 할 일1")
                .scheduleEndDate(LocalDateTime.now())
                .scheduleCreator(user)
                .build());
        Schedule schedule = scheduleRepository.findScheduleByScheduleTitle("유저의 할 일1");


        ScheduleAssign scheduleAssign = scheduleAssignRepository.save(
                ScheduleAssign.builder()
                        .scheduleAssigneeId(user.getUserId())
                        .scheduleAssigneeCategory(categoryCode)
                        .schedule(schedule)
                        .build());

        //할일 배정2 : 유저의 부서에 배정

        scheduleRepository.save(Schedule.builder()
                .scheduleDivision(scheduleDivisionCode)
                .scheduleTitle("유저의 할 일2")
                .scheduleEndDate(LocalDateTime.now())
                .scheduleCreator(user)
                .build());
        Schedule schedule2 = scheduleRepository.findScheduleByScheduleTitle("유저의 할 일2");


        ScheduleAssign scheduleAssign2 = scheduleAssignRepository.save(ScheduleAssign.builder()
                .scheduleAssigneeId(department.getDepartmentId())
                .scheduleAssigneeCategory(categoryCode2)
                .schedule(schedule2)
                .build());

        List<ScheduleAssignSearchParameterDto> requestDtoList = new ArrayList<>();

        requestDtoList.add(ScheduleAssignSearchParameterDto.builder()
                .scheduleAssigneeId(user.getUserId())
                .scheduleCategoryCodeId(categoryCode.getCodeId())
                .build());

        requestDtoList.add(ScheduleAssignSearchParameterDto.builder()
                .scheduleAssigneeId(department.getDepartmentId())
                .scheduleCategoryCodeId(categoryCode2.getCodeId())
                .build());

        System.out.println("requestDtoList 준비 완료!");
        for (ScheduleAssignSearchParameterDto requestDto : requestDtoList){
            System.out.println(requestDto);
        }

        System.out.println("이제 이 데이터로 조회 시작 ! requestDtos = " + requestDtoList);
        System.out.println("할일 배정 데이터 전체 = " + scheduleAssignRepository.findAll());

        //when
        List<ScheduleAssignResponseDto> responseDtoList = scheduleAssignRepository.findScheduleAssignsBySearchParameter(requestDtoList);

        //then
//      assertNull(responseDtoList);
        assertEquals(2, responseDtoList.size());

        for (ScheduleAssignResponseDto responseDto : responseDtoList){
            System.out.println(responseDto);
        }
    }

    @Test
    void findScheduleAssignees() {

        User user = User.builder()
                .userDepartment(new Department())
                .build();
        userRepository.save(user);

        Code categoryCode = Code.builder()
                .codeName("유저")
                .codeCodeGroup(new CodeGroup())
                .build();
        codeRepository.save(categoryCode);


        ScheduleAssign scheduleAssign = ScheduleAssign.builder()
                .scheduleAssigneeId(user.getUserId())
                .scheduleAssigneeCategory(categoryCode)
                .schedule(Schedule.builder().scheduleTitle("할 일1").build())
                .build();
        scheduleAssignRepository.save(scheduleAssign);

        ScheduleAssign scheduleAssign2 = ScheduleAssign.builder()
                .scheduleAssigneeId(user.getUserId())
                .scheduleAssigneeCategory(categoryCode)
                .schedule(Schedule.builder().scheduleTitle("할 일2").build())
                .build();
        scheduleAssignRepository.save(scheduleAssign2);


        //when
        List<ScheduleAssignSearchParameterDto> requestDtoList = new ArrayList<>();
        requestDtoList.add(ScheduleAssignSearchParameterDto.builder()
                .scheduleAssigneeId(user.getUserId())
                .scheduleCategoryCodeId(categoryCode.getCodeId())
                .build());

        List<ScheduleAssignResponseDto> responseDtoList = scheduleAssignRepository.findScheduleAssignsBySearchParameter(requestDtoList);

        //then
        assertEquals(2, responseDtoList.size());

        for (ScheduleAssignResponseDto responseDto : responseDtoList){
            System.out.println(responseDto);
        }

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
        userRepository.save(user);

        Schedule schedule = Schedule.builder()
                .scheduleDivision(new Code())
                .scheduleEndDate(LocalDateTime.now())
                .schedulePlace("회의실B")
                .scheduleCreator(user)
                .build();
        scheduleRepository.save(schedule);

        Schedule schedule2 = Schedule.builder()
                .scheduleDivision(new Code())
                .scheduleEndDate(LocalDateTime.now())
                .schedulePlace("공원")
                .scheduleCreator(user)
                .build();
        scheduleRepository.save(schedule2);

        Schedule schedule3 = Schedule.builder()
                .scheduleDivision(new Code())
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