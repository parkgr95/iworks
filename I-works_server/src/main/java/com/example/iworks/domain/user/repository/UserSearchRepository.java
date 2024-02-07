package com.example.iworks.domain.user.repository;

import com.example.iworks.domain.user.domain.User;

import java.util.List;

public interface UserSearchRepository {
    List<User> getUserListByUserList(List<Integer> dto);
}
