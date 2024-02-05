package com.example.iworks.domain.address.service;

import com.example.iworks.domain.address.dto.AddressDepartmentResonseDto;
import com.example.iworks.domain.address.dto.AddressDto;
import com.example.iworks.domain.address.dto.AddressTeamResponseDto;
import com.example.iworks.domain.address.dto.AddressUserResponseDto;
import com.example.iworks.domain.address.respository.AddressRepository;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;
    private final Response response;

    @Override
    public ResponseEntity<Map<String,Object>> selectAddressAll()
    {
        List<AddressDto> list =  addressRepository.selectAddressAll();
        if(list.isEmpty()){
            return response.handleFail("조회 내용 없음.");
        }

        Stream<AddressUserResponseDto> result = list.stream()
                .filter(item-> item.getUser().getUserIsDeleted()!=null).map(AddressUserResponseDto::new);

        return response.handleSuccess(result);
    }



    @Override
    public ResponseEntity<Map<String, Object>> selectDepartmentAll() {
        Stream<AddressDepartmentResonseDto> result = departmentRepository.findAll().stream()
                .filter(Department::isDepartmentIsUsed).map(AddressDepartmentResonseDto::new);
        return response.handleSuccess(result);
    }

    @Override
    public ResponseEntity<Map<String, Object>> selectTeamAll() {
        Stream<AddressTeamResponseDto> result = teamRepository.findAll().stream()
                .filter(Team::getTeamIsDeleted).map(AddressTeamResponseDto::new);
        return response.handleSuccess(result);
    }
}
