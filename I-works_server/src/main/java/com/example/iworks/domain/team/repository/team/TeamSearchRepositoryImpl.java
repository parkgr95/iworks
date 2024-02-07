package com.example.iworks.domain.team.repository.team;

import com.example.iworks.domain.team.domain.QTeam;
import com.example.iworks.domain.team.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TeamSearchRepositoryImpl implements  TeamSearchRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public Team findAvailableTeamByTeamName(String teamName){
        QTeam t = new QTeam("t");
        return jpaQueryFactory
                .select(t)
                .from(t)
                .where(t.teamIsDeleted.isFalse())
                .fetchOne();
    }

}
