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
    public List<UserNotification> findByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    @Override
    public List<UserNotification> findCategoryBoardByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isBoard()).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    @Override
    public List<UserNotification> findCategoryScheduleByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isSchedule()).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    @Override
    public List<UserNotification> findCategoryMeetingByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isMeeting()).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
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
