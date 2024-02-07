package com.example.iworks.domain.team.repository.teamuser;

import com.example.iworks.domain.team.domain.QTeamUser;
import com.example.iworks.domain.team.domain.TeamUser;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.domain.team.repository.teamuser.custom.TeamUserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.iworks.domain.team.domain.QTeamUser.teamUser;

public class TeamUserRepositoryImpl implements TeamUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public TeamUserRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public List<TeamUser> findTeamUserByUserId(int userId) {
        return jpaQueryFactory
                .selectFrom(teamUser)
                .where(teamUser.teamUserUser.userId.eq(userId))
                .fetch();
    }
}
