package com.example.iworks.domain.team.repository.team;

import com.example.iworks.domain.team.domain.Team;

import java.util.List;

public interface TeamSearchRepository {
    public Team findAvailableTeamByTeamName(String teamName);

    public List<Team> findTeamAllByUserId(int userId);
}
