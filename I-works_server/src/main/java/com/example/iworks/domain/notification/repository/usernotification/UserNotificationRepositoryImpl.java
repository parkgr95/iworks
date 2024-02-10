package com.example.iworks.domain.notification.repository.usernotification;

import com.example.iworks.domain.notification.domain.UserNotification;
import com.example.iworks.domain.notification.repository.usernotification.custom.UserNotificationSearchRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.iworks.domain.notification.domain.QUserNotification.userNotification;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class UserNotificationRepositoryImpl implements UserNotificationSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserNotification> findAllByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    @Override
    public List<UserNotification> findAllCategoryBoardByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isBoard()).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    @Override
    public List<UserNotification> findAllCategoryScheduleByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isSchedule()).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    @Override
    public List<UserNotification> findAllCategoryMeetingByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isMeeting()).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    @Override
    public long countOfIsNotSent(int userId) {
        return queryFactory
                .select(userNotification.count())
                .from(userNotification)
                .where(eqReceiverId(userId).and(isNotSent()))
                .fetchCount();
    }

    private BooleanExpression isNotSent() {
        return userNotification.userNotificationIsSent.eq(false);
    }

    private BooleanExpression isBoard() {
        return userNotification.userNotificationBoard.isNotNull();
    }

    private BooleanExpression isSchedule() {
        return userNotification.userNotificationSchedule.isNotNull();
    }

    private BooleanExpression isMeeting() {
        return userNotification.userNotificationMeeting.isNotNull();
    }

    private BooleanExpression eqReceiverId(int userId) {
        return userNotification.userNotificationReceiver.userId.eq(userId);
    }

    private BooleanExpression isNotDeleted() {
        return userNotification.userNotificationIsDeleted.eq(false);
    }
}
