package com.example.iworks.domain.schedule.repository.schedule;


import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.repository.schedule.custom.ScheduleSearchRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.iworks.domain.schedule.domain.QSchedule.schedule;
import static java.util.stream.Collectors.toList;

//@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScheduleResponseDto> findByKeyword(String keyword) {
        return queryFactory
                .selectFrom(schedule)
                .where(containsTitle(keyword)
                        .or(containsCreatorName(keyword))
                        .and(schedule.scheduleIsDeleted.isFalse())
                )
                .fetch()
                .stream()
                .map(ScheduleResponseDto::new)
                .collect(toList());
    }

    private BooleanExpression containsTitle(String keyword){
        return schedule.scheduleTitle.like("%"+keyword+"%");
    }
    private BooleanExpression containsCreatorName(String keyword){
        return schedule.scheduleCreator.userNameFirst.like("%"+keyword+"%");
    }
}
