package com.example.iworks.domain.team.repository.teamuser.custom;

import com.example.iworks.domain.team.domain.TeamUser;

import java.util.List;

public interface TeamUserRepositoryCustom {
    List<TeamUser> findTeamUserByUserId(int userId);
}
