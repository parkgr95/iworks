package com.example.iworks.domain.address.service;

import com.example.iworks.domain.address.dto.AddressTeamCreateRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AddressService {
    ResponseEntity<Map<String,Object>> selectAddressAll();

    ResponseEntity<Map<String,Object>> createTeam(AddressTeamCreateRequestDto requestDto);

    ResponseEntity<Map<String,Object>> selectDepartmentAll();

    ResponseEntity<Map<String,Object>> selectTeamAll();
}
