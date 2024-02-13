package com.example.iworks.domain.code.repository;

import com.example.iworks.domain.code.dto.CodeResponseDto;

import java.util.List;

public interface CodeSearchRepository {
    List<CodeResponseDto> getCodeGroupAll(int codeGroupId);
}
