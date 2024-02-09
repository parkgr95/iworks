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

    /** 정렬 */
    @Override
    public List<UserNotification> findUserNotificationsByUserId(int userId) {
        return queryFactory
                .selectFrom(userNotification)
                .where( eqReceiverId(userId).and(isNotDeleted()))
                .orderBy(userNotification.userNotificationCreatedAt.asc())
                .fetch();
    }

    private BooleanExpression eqReceiverId(int userId) {
        return userNotification.userNotificationReceiver.userId.eq(userId);
    }
    private BooleanExpression isNotDeleted() {
        return userNotification.userNotificationIsDeleted.eq(false);
    }
}
