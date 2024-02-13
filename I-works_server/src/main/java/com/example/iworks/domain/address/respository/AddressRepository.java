package com.example.iworks.domain.address.respository;

import com.example.iworks.domain.address.entity.Address;
import com.example.iworks.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<User,Integer> {

    @Query("select u as user, d as department, c as code from User u left join fetch u.userDepartment d left join u.userPositionCode c")
    List<Address> selectAddressAll();


}
