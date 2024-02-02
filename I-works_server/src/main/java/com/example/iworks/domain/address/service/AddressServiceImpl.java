package com.example.iworks.domain.address.service;

import com.example.iworks.domain.address.dto.AddressDto;
import com.example.iworks.domain.address.dto.AddressResponseDto;
import com.example.iworks.domain.address.respository.AddressRepository;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final Response response;

    @Override
    public ResponseEntity<Map<String,Object>> selectAddressAll()
    {
        List<AddressDto> result =  addressRepository.selectAddressAll();
        if(result.isEmpty()){
            return response.handleFail("조회 내용 없음.");
        }
        Map<Integer, AddressResponseDto> data = new HashMap<>();
        for(int i=0; i< result.size(); i++){
            data.put(i+1,new AddressResponseDto(result.get(i)));
        }
        return response.handleSuccess(data);
    }
}
