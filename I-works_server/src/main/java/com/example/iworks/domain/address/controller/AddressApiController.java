package com.example.iworks.domain.address.controller;

import com.example.iworks.domain.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}
