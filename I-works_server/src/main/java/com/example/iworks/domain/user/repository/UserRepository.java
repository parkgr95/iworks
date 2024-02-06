package com.example.iworks.domain.user.repository;

import com.example.iworks.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserEid(String userEid);

    public User findByUserEmail(String userEmail);
}
