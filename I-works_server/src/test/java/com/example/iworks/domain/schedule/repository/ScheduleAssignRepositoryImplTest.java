package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignFindBySearchParameterResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.schedule.repository.scheduleAssign.ScheduleAssignRepository;
import com.example.iworks.domain.schedule.repository.scheduleAssign.custom.ScheduleAssignRepositoryCustom;
import com.example.iworks.domain.schedule.repository.scheduleAssign.ScheduleAssignRepositoryImpl;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.entity.CodeGroup;
import com.example.iworks.global.model.repository.CodeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ScheduleAssignRepositoryImplTest {

   @Autowired
   UserRepository userRepository;

   @Autowired
   ScheduleRepository scheduleRepository;

   @Autowired
   CodeRepository codeRepository;

   ScheduleAssignRepository scheduleAssignRepository;
   ScheduleAssignRepositoryCustom scheduleAssignRepositoryCustom;

   @PersistenceContext
   EntityManager entityManager;

   public ScheduleAssignRepositoryImplTest(ScheduleAssignRepository scheduleAssignRepository, ScheduleAssignRepositoryCustom scheduleAssignRepositoryCustom) {
      this.scheduleAssignRepository = scheduleAssignRepository;
      this.scheduleAssignRepositoryCustom = new ScheduleAssignRepositoryImpl(entityManager);
   }

   @Test
   void findScheduleAssignees() {
      //given
      User user = User.builder()
              .userDepartment(new Department())
              .build();
      userRepository.save(user);

      Code categoryCode = Code.builder()
              .codeName("유저")
              .codeCodeGroup(new CodeGroup())
              .build();
      codeRepository.save(categoryCode); //생략하면?


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

      List<ScheduleAssignFindBySearchParameterResponseDto> responseDtoList = scheduleAssignRepositoryCustom.findScheduleAssignsBySearchParameter(requestDtoList);

      //then
      assertEquals(2, responseDtoList.size());

      for (ScheduleAssignFindBySearchParameterResponseDto responseDto : responseDtoList){
         System.out.println(responseDto);
      }

   }
}