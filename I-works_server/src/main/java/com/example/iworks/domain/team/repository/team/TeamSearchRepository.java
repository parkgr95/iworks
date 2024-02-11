package com.example.iworks.domain.team.repository.team;

import com.example.iworks.domain.team.domain.Team;

public interface TeamSearchRepository {
    public Team findAvailableTeamByTeamName(String teamName);
}
