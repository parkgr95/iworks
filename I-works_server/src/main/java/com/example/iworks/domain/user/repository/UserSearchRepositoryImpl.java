package com.example.iworks.domain.user.repository;

import com.example.iworks.domain.user.domain.QUser;
import com.example.iworks.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserSearchRepositoryImpl implements UserSearchRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> getUserListByUserList(List<Integer> dto) {
        QUser u = new QUser("u");
        return jpaQueryFactory
                .select(u)
                .from(u)
                .where(u.userId.in(dto))
                .stream().toList();
    }
}
