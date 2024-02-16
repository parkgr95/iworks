package com.example.iworks.domain.user.repository.custom;

import com.example.iworks.domain.user.domain.User;

import java.util.List;

public interface UserSearchRepository {
    List<User> getUserListByUserIds(List<Integer> dto);

    User getAvailableUserByEmail(String email);
}
