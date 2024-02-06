package com.example.iworks.domain.address.dto;

import com.example.iworks.domain.team.domain.TeamUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressTeamUserAddRequestDto {
    private int teamId;
    private List<TeamUser> teamUsers;
}
