package com.example.iworks.domain.address.service;

import com.example.iworks.domain.address.dto.*;
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

import java.util.HashMap;
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
            return response.handleFail("조회 내용 없음.",null);
        }

        Stream<AddressUserResponseDto> result = list.stream()
                .filter(item-> !item.getUser().getUserIsDeleted()).map(AddressUserResponseDto::new);

        return response.handleSuccess(result);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String,Object>> createTeam(AddressTeamCreateRequestDto requestDto)
    {
        try {
            Team team = new Team(requestDto);
            if(teamRepository.findByTeamName(team.getTeamName())!= null){
                return response.handleFail("이미 존재하는 팀 이름입니다.",null);
            }
            Team result = teamRepository.save(team);
            HashMap<String,Object> map = new HashMap<>();
            map.put("teamId",result.getTeamId());
            map.put("message","팀이 생성 되었습니다.");
            return response.handleSuccess(map);
        }catch (Exception e){
            return response.handleFail("생성 실패",e.getMessage());
        }
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
