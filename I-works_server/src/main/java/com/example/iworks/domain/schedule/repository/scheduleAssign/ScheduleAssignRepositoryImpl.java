package com.example.iworks.domain.schedule.repository.scheduleAssign;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.repository.scheduleAssign.custom.ScheduleAssignGetRepository;
import com.example.iworks.global.dto.DateCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.iworks.domain.schedule.domain.QSchedule.schedule;
import static com.example.iworks.domain.schedule.domain.QScheduleAssign.scheduleAssign;
import static com.example.iworks.global.common.CodeDef.*;


@Repository
@RequiredArgsConstructor
public class ScheduleAssignRepositoryImpl implements ScheduleAssignGetRepository {

   private final JPAQueryFactory jpaQueryFactory;

   @Override
   public List<ScheduleAssign> findScheduleAssignsBySearchParameter(List<AssigneeInfo> requestDtoList, DateCondition dateCondition, boolean onlyTask) {
      Set<ScheduleAssign> foundScheduleAssignList = new HashSet<>();

      for (AssigneeInfo requestDto : requestDtoList){
         List<ScheduleAssign> foundScheduleAssign =
                 jpaQueryFactory
                         .selectFrom(scheduleAssign)
                         .join(scheduleAssign.schedule, schedule).fetchJoin()
                         .where(eqCategoryCodeId(requestDto.getCategoryCodeId())
                                 .and(eqAssigneeId(requestDto.getAssigneeId()))
                                 ,schedule.scheduleIsFinish.eq(false)
                                 ,withInDate(dateCondition)
                                 ,filterTask(onlyTask)
                         )
                         .distinct()
                         .fetch();

         foundScheduleAssignList.addAll(foundScheduleAssign);
      }
      return new ArrayList<>(foundScheduleAssignList);
   }
   private BooleanExpression filterTask(boolean onlyTask){
      if (!onlyTask) return Expressions.TRUE;
      return scheduleAssign.schedule.scheduleDivision.codeId.eq(SCHEDULE_DIVISION_TASK_CODE_ID);
   }
   private BooleanExpression eqCategoryCodeId (int categoryCodeId){
      return scheduleAssign.scheduleAssigneeCategory.codeId.eq(categoryCodeId);
   }
   private BooleanExpression eqAssigneeId (int assigneeId){
      return scheduleAssign.scheduleAssigneeId.eq(assigneeId);
   }
   private BooleanExpression withInDate (DateCondition dateCondition){
      if (dateCondition == null) return Expressions.TRUE;
      return schedule.scheduleStartDate.loe(dateCondition.getEndDate())
              .and(schedule.scheduleEndDate.goe(dateCondition.getStartDate()));
   }

}
