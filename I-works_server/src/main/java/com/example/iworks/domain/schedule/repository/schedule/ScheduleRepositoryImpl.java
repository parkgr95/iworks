package com.example.iworks.domain.schedule.repository.schedule;


import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.repository.schedule.custom.ScheduleSearchRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;
import static com.example.iworks.domain.schedule.domain.QSchedule.schedule;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleSearchRepository {

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
    private BooleanExpression containsKeyword(String keyword){
        return schedule.scheduleTitle.like("%"+keyword+"%")
                .or(schedule.scheduleContent.like("%"+keyword+"%"))
                .or(schedule.schedulePlace.like("%"+keyword+"%"))
                .or(schedule.scheduleCreator.userNameFirst.like("%"+keyword+"%"));
    }

}
