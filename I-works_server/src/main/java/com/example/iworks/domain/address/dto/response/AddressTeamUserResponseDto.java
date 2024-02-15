package com.example.iworks.domain.address.dto.response;

import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.dto.UserGetMyPageResponseDto;
import lombok.Getter;

@Getter
public class AddressTeamUserResponseDto {
    private final Integer teamUserId;
    private final UserGetMyPageResponseDto userDto;

    public AddressTeamUserResponseDto(Integer teamUserId, User user) {
        this.teamUserId = teamUserId;
        this.userDto = new UserGetMyPageResponseDto(user);
    }


}
