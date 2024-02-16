package com.example.iworks.domain.team.repository.team;

import com.example.iworks.domain.team.domain.Team;

import java.util.List;

public interface TeamSearchRepository {
    Team findAvailableTeamByTeamName(String teamName);

    List<Team> findTeamAllByUserId(int userId);
}
