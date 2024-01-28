package com.example.iworks.domain.schedule.repository;
import com.example.iworks.domain.schedule.dto.QScheduleAssignResponseDto;
import com.example.iworks.domain.schedule.dto.ScheduleAssignRequestDto;
import com.example.iworks.domain.schedule.dto.ScheduleAssignResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import static com.example.iworks.domain.schedule.domain.QScheduleAssign.scheduleAssign;


public class ScheduleAssignRepositoryImpl implements ScheduleAssignRepositoryCustom{

   private final JPAQueryFactory jpaQueryFactory;

   public ScheduleAssignRepositoryImpl(EntityManager entityManager) {
      this.jpaQueryFactory = new JPAQueryFactory(entityManager);
   }

   @Override
   public List<ScheduleAssignResponseDto> findScheduleAssignees(List<ScheduleAssignRequestDto> requestDtoList) {
      return jpaQueryFactory
              .select(new QScheduleAssignResponseDto(
                      scheduleAssign.schedule.scheduleId,
                      scheduleAssign.schedule.scheduleDivisionId,
                      scheduleAssign.schedule.scheduleTitle,
                      scheduleAssign.schedule.scheduleStartDate,
                      scheduleAssign.schedule.scheduleEndDate
//                      scheduleAssign.
              ))
              .from(scheduleAssign)
              .where(buildDynamicConditions(requestDtoList))
              .fetch();
   }

   private BooleanExpression buildDynamicConditions(List<ScheduleAssignRequestDto> requestDtoList){
      BooleanExpression conditions = null;
      for (ScheduleAssignRequestDto requestDto : requestDtoList){
         BooleanExpression condition = scheduleAssign.scheduleAssigneeCategory.codeId.eq(requestDto.getScheduleCategoryCodeId())
                 .and(scheduleAssign.scheduleAssigneeId.eq(requestDto.getScheduleAssigneeId()));

         conditions = (condition == null) ? condition : conditions.or(condition);

      }
      System.out.println("DynamicConditions : "+ conditions);
      return conditions;
   }
}
