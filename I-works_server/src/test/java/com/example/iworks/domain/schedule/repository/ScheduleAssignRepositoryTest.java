package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.scheduleAssign.response.ScheduleAssignResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.schedule.repository.scheduleAssign.ScheduleAssignRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.entity.CodeGroup;
import com.example.iworks.global.model.repository.CodeGroupRepository;
import com.example.iworks.global.model.repository.CodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
class ScheduleAssignRepositoryTest {

   @Autowired
   UserRepository userRepository;

   @Autowired
   ScheduleRepository scheduleRepository;

   @Autowired
   CodeRepository codeRepository;

   @Autowired
   CodeGroupRepository codeGroupRepository;

   @Autowired
   DepartmentRepository departmentRepository;

   @Autowired
   ScheduleAssignRepository scheduleAssignRepository;

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


//      schedule.addScheduleAssigns(scheduleAssign);
       ;
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

   private void saveSeveralScheduleAssigns(User user, Code categoryCode, Code categoryCode2, Department department) {

   }
}