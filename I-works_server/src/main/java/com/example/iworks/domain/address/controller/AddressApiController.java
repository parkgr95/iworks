package com.example.iworks.domain.address.controller;

import com.example.iworks.domain.address.dto.AddressTeamCreateRequestDto;
import com.example.iworks.domain.address.service.AddressService;
import com.example.iworks.domain.team.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/address")
@RestController
@RequiredArgsConstructor
public class AddressApiController {
    private final AddressService addressService;


    @GetMapping("/department/all")
    public ResponseEntity<Map<String,Object>> getDepartmentAll(){
        return addressService.selectDepartmentAll();
    }

    @GetMapping("/team/all")
    public ResponseEntity<Map<String,Object>> getTeamAll(){
        return addressService.selectTeamAll();
    }

    @GetMapping("/user/all")
    public ResponseEntity<Map<String,Object>> getAddressAll(){
        return addressService.selectAddressAll();
    }

    @PostMapping("/team/create")
    public ResponseEntity<Map<String,Object>> createTeam(@RequestBody AddressTeamCreateRequestDto requestDto){
        return addressService.createTeam(requestDto);
    }


}
