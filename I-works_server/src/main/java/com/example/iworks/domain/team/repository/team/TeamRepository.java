package com.example.iworks.domain.team.repository.team;

import com.example.iworks.domain.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Team findByTeamName(String teamName);
    Team findByTeamId(int teamId);
}
