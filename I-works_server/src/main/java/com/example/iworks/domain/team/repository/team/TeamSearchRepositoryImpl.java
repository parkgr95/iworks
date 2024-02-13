package com.example.iworks.domain.team.repository.team;

import com.example.iworks.domain.team.domain.QTeam;
import com.example.iworks.domain.team.domain.QTeamUser;
import com.example.iworks.domain.team.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamSearchRepositoryImpl implements  TeamSearchRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public Team findAvailableTeamByTeamName(String teamName){
        QTeam t = new QTeam("t");
        return jpaQueryFactory
                .select(t)
                .from(t)
                .where(
                        t.teamIsDeleted.isFalse()
                                .and(t.teamName.eq(teamName))
                )
                .fetchOne();
    }

    @Override
    public List<Team> findTeamAllByUserId(int userId) {
        QTeam t = new QTeam("t");
        QTeamUser tu = new QTeamUser("tu");
        return jpaQueryFactory
                .select(t)
                .from(t)
                .join(tu)
                .on(t.teamId.eq(tu.teamUserTeam.teamId))
                .where(
                        t.teamIsDeleted.isFalse()
                                .and(tu.teamUserUser.userId.eq(userId))
                ).stream().toList();
    }

}
