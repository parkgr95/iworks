package com.example.iworks.domain.schedule.repository.schedule;


import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.repository.schedule.custom.ScheduleAssignSearchRepository;
import com.example.iworks.domain.schedule.repository.schedule.custom.ScheduleSearchRepository;
import com.example.iworks.global.dto.DateCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.iworks.domain.schedule.domain.QSchedule.schedule;
import static com.example.iworks.domain.schedule.domain.QScheduleAssign.scheduleAssign;
import static com.example.iworks.global.common.CodeDef.SCHEDULE_DIVISION_TASK_CODE_ID;
import static com.querydsl.jpa.JPAExpressions.select;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleSearchRepository, ScheduleAssignSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScheduleResponseDto> findByKeyword(String keyword) {
        return queryFactory
                .selectFrom(schedule)
                .where(containsKeyword(keyword)
                        .and(notDeleted())
                )
                .fetch()
                .stream()
                .map(ScheduleResponseDto::new)
                .collect(toList());
    }

    private BooleanExpression notDeleted() {
        return schedule.scheduleIsDeleted.isFalse();
    }

    private BooleanExpression containsKeyword(String keyword) {
        return schedule.scheduleTitle.like("%" + keyword + "%")
                .or(schedule.scheduleContent.like("%" + keyword + "%"))
                .or(schedule.schedulePlace.like("%" + keyword + "%"))
                .or(schedule.scheduleCreator.userNameFirst.like("%" + keyword + "%"));
    }

    /** Moved from ScheduleAssign*/
    @Override
    public List<Schedule> findScheduleByAssigneeInfo(List<AssigneeInfo> requestDtoList, DateCondition dateCondition, boolean onlyTask) {
        Set<Schedule> scheduleSet = new HashSet<>();
        for (AssigneeInfo requestDto : requestDtoList) {
            List<Schedule> foundSchedules =
                    queryFactory
                            .selectFrom(schedule)
                            .join(schedule.scheduleAssigns, scheduleAssign)//.fetchJoin()
                            .where(schedule.scheduleId.in(
                                    select(scheduleAssign.schedule.scheduleId)
                                            .from(scheduleAssign)
                                            .where(eqAssignee(requestDto.getCategoryCodeId(), requestDto.getAssigneeId())
                                                            .and(notDeleted())
                                                    , withInDate(dateCondition)
                                                    , filterTask(onlyTask)
                                            )
                            ))
                            .fetch();

            scheduleSet.addAll(foundSchedules);
        }
        return new ArrayList<>(scheduleSet);
    }
    private BooleanExpression filterTask(boolean onlyTask){
        if (!onlyTask) return Expressions.TRUE;
        return scheduleAssign.schedule.scheduleDivision.codeId.eq(SCHEDULE_DIVISION_TASK_CODE_ID);
    }
    private BooleanExpression eqCategoryCodeId (int categoryCodeId){
        return scheduleAssign.scheduleAssigneeCategory.codeId.eq(categoryCodeId);
    }
    private BooleanExpression eqAssigneeId(int assigneeId){
        return scheduleAssign.scheduleAssigneeId.eq(assigneeId);
    }
    private BooleanExpression eqAssignee(int categoryCodeId, int assigneeId){
        return eqCategoryCodeId(categoryCodeId).and(eqAssigneeId(assigneeId));
    }
    private BooleanExpression withInDate(DateCondition dateCondition){
        if (dateCondition == null) return Expressions.TRUE;
        return schedule.scheduleStartDate.loe(dateCondition.getEndDate())
                .and(schedule.scheduleEndDate.goe(dateCondition.getStartDate()));
    }

}
