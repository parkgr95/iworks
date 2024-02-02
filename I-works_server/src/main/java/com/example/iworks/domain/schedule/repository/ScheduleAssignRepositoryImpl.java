package com.example.iworks.domain.schedule.repository;
import com.example.iworks.domain.schedule.domain.QSchedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.QScheduleAssignResponseDto;
import com.example.iworks.domain.schedule.dto.ScheduleAssignRequestDto;
import com.example.iworks.domain.schedule.dto.ScheduleAssignResponseDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.iworks.domain.schedule.domain.QSchedule.schedule;
import static com.example.iworks.domain.schedule.domain.QScheduleAssign.scheduleAssign;


@Repository
public class ScheduleAssignRepositoryImpl implements ScheduleAssignRepositoryCustom{

   private final JPAQueryFactory jpaQueryFactory;

   public ScheduleAssignRepositoryImpl(EntityManager entityManager) {
      this.jpaQueryFactory = new JPAQueryFactory(entityManager);
   }

   //Set으로 할 수 있음?
   @Override
   public List<ScheduleAssignResponseDto> findScheduleAssignees(List<ScheduleAssignRequestDto> requestDtoList) {
      List<ScheduleAssign> foundScheduleAssignList = new ArrayList<>();

      for (ScheduleAssignRequestDto requestDto : requestDtoList){
         List<ScheduleAssign> foundScheduleAssign =
                 jpaQueryFactory
                         .selectFrom(scheduleAssign)
                         .join(scheduleAssign.schedule, schedule).fetchJoin()
                         .where(eqCategoryCodeId(requestDto.getScheduleCategoryCodeId())
                                 .and(eqAssigneeId(requestDto.getScheduleAssigneeId())))
                         .fetch();

         foundScheduleAssignList.addAll(foundScheduleAssign);
      }

      System.out.println("------------------------");
      for (ScheduleAssign scheduleAssign:foundScheduleAssignList){
         System.out.println(scheduleAssign);
      }
      return mapToScheduleAssignResponseDto(foundScheduleAssignList);

   }

   private List<ScheduleAssignResponseDto> mapToScheduleAssignResponseDto(List<ScheduleAssign> scheduleAssignList) {
      List<ScheduleAssignResponseDto> mappedResponseDtoList = new ArrayList<>();
      for (ScheduleAssign scheduleAssign : scheduleAssignList){
         mappedResponseDtoList.add(
                 ScheduleAssignResponseDto.builder()
                         .scheduleId(scheduleAssign.getSchedule().getScheduleId())
                         .scheduleTitle(scheduleAssign.getSchedule().getScheduleTitle())
                         .scheduleDivisionName(scheduleAssign.getSchedule().getScheduleDivision().getCodeName())
                         .scheduleStartDate(scheduleAssign.getSchedule().getScheduleStartDate())
                         .scheduleEndDate(scheduleAssign.getSchedule().getScheduleEndDate())
                         .build()
         );
      }
      return mappedResponseDtoList;
   }

   private BooleanExpression eqCategoryCodeId (int categoryCodeId){
      return scheduleAssign.scheduleAssigneeCategory.codeId.eq(categoryCodeId);
   }
   private BooleanExpression eqAssigneeId (int assigneeId){
      return scheduleAssign.scheduleAssigneeId.eq(assigneeId);
   }


}
