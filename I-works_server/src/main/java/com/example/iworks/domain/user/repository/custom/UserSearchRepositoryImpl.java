package com.example.iworks.domain.user.repository.custom;

import com.example.iworks.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.iworks.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserSearchRepositoryImpl implements UserSearchRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> getUserListByUserIds(List<Integer> dto) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.userId.in(dto))
                .stream().toList();
    }

    @Override
    public User getAvailableUserByEmail(String email) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.userEmail.eq(email).and(user.userIsDeleted.isFalse())).fetchOne();
    }

    @Override
    public User getAvailableUserByEid(String eid) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.userEmail.eq(eid).and(user.userIsDeleted.isFalse())).fetchOne();
    }

}
