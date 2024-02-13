package com.example.iworks.domain.user.repository;

import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.custom.UserSearchRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.iworks.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> getUserListByUserIds(List<Integer> dto) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.userId.in(dto))
                .stream().toList();
    }
}
