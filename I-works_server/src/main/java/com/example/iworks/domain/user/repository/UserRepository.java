package com.example.iworks.domain.user.repository;

import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.custom.UserSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>, UserSearchRepository {

    User findByUserEid(String userEid);

    User findByUserId(int userId);

    User findByUserEmail(String userEmail);

    @Query("select u from User u join u.userDepartment d where d.departmentId = :departmentId")
    List<User> findUsersByDepartmentId(@Param("departmentId") Integer departmentId);

    @Query("select u from User u join u.userTeamUsers tu where tu.teamUserTeam.teamId = :teamId")
    List<User> findUsersByTeamId(@Param("teamId") Integer teamId); //throws There's No data
}
