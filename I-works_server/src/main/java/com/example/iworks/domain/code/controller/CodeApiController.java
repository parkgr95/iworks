package com.example.iworks.domain.code.controller;

import com.example.iworks.domain.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeApiController {
    private final CodeService codeService;
    @GetMapping("/category")
    public ResponseEntity<Map<String,Object>> getCategoryCodeAll(){
        return codeService.getCategoryCodeAll();
    }

    @GetMapping("/target")
    public ResponseEntity<Map<String,Object>> getTargetCodeAll(){
        return codeService.getTargetCodeAll();
    }


    @GetMapping("/status")
    public ResponseEntity<Map<String,Object>> getStatusCodeAll(){
        return codeService.getStatusCodeAll();
    }

    @GetMapping("/position")
    public ResponseEntity<Map<String,Object>> getPositionCodeAll(){
        return codeService.getPositionCodeAll();
    }

    @GetMapping("/schedule-division")
    public ResponseEntity<Map<String,Object>> getScheduleDivisionCodeAll(){
        return codeService.getScheduleDivisionCodeAll();
    }

}
