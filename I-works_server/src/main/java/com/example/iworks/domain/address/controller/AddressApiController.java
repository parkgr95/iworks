package com.example.iworks.domain.address.controller;

import com.example.iworks.domain.address.service.AddressService;
import com.example.iworks.global.util.OpenViduUtil;
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
    private final OpenViduUtil openViduUtil;

    @GetMapping("/")
    public ResponseEntity<String> test(){

        return openViduUtil.getSessionClientsNumber("edith-montserrat-crimson-anaconda");
    }
    @GetMapping("/org-chart")
    public ResponseEntity<Map<String,Object>> getOrganizationChart(){

        return addressService.selectDepartmentAll();
    }

    @GetMapping("/search/all")
    public ResponseEntity<Map<String,Object>> getAddressAll(){
        return addressService.selectAddressAll();
    }




}
