package com.example.iworks.domain.address.service;

import com.example.iworks.domain.address.dto.AddressTeamCreateRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AddressService {
    ResponseEntity<Map<String,Object>> selectAddressAll();

    ResponseEntity<Map<String,Object>> createTeam(AddressTeamCreateRequestDto requestDto);

    ResponseEntity<Map<String,Object>> selectDepartmentAll();

    ResponseEntity<Map<String,Object>> selectTeamAll();

    ResponseEntity<Map<String, Object>> deleteTeam(int teamId, String token);

    ResponseEntity<Map<String, Object>> addTeamUser(int teamId, String token, int targetId);

    ResponseEntity<Map<String, Object>> removeTeamUser(int teamId, String token, int targetId);
}
