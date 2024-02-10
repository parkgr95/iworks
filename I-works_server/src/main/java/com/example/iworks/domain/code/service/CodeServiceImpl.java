package com.example.iworks.domain.code.service;

import com.example.iworks.domain.code.repository.CodeSearchRepository;
import com.example.iworks.global.common.CodeGroupDef;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeSearchRepository codeSearchRepository;
    private final Response response;

    @Override
    public ResponseEntity<Map<String, Object>> getPositionCodeAll() {
        return response.handleSuccess(codeSearchRepository.getCodeGroupAll(CodeGroupDef.CODE_GROUP_POSITION));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getCategoryCodeAll() {
        return response.handleSuccess(codeSearchRepository.getCodeGroupAll(CodeGroupDef.CODE_GROUP_CATEGORY));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getStatusCodeAll() {
        return response.handleSuccess(codeSearchRepository.getCodeGroupAll(CodeGroupDef.CODE_GROUP_STATUS));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTargetCodeAll() {
        return response.handleSuccess(codeSearchRepository.getCodeGroupAll(CodeGroupDef.CODE_GROUP_TARGET));
    }
}
