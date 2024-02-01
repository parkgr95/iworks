package com.example.iworks.domain.user.controller;

import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.dto.UserDto;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.Response;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final Response response;

    @GetMapping("/sample/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") int userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("error"));
        UserDto userDto = new UserDto(userId, user.getUserNameFirst(),user.getUserDepartment().getDepartmentName(), user.getUserDepartment().getDepartmentId());
//        return new ResponseEntity<>(userDto, HttpStatus.OK);
        return response.handleSuccess(userDto);
    }

}
