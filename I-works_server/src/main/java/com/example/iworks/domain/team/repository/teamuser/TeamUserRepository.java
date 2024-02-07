package com.example.iworks.domain.team.repository.teamuser;

import com.example.iworks.domain.team.domain.TeamUser;
import com.example.iworks.domain.team.repository.teamuser.custom.TeamUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamUserRepository extends JpaRepository<TeamUser, Integer>, TeamUserRepositoryCustom{
}
