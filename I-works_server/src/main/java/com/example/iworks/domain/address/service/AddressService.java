package com.example.iworks.domain.address.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AddressService {
    ResponseEntity<Map<String,Object>> selectAddressAll();
    ResponseEntity<Map<String,Object>> selectDepartmentAll();

    ResponseEntity<Map<String,Object>> selectTeamAll();
}
