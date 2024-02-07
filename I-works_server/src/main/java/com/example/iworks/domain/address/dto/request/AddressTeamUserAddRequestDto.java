package com.example.iworks.domain.address.dto.request;

import com.example.iworks.domain.team.domain.TeamUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressTeamUserAddRequestDto {
    private List<TeamUser> teamUsers;
}
