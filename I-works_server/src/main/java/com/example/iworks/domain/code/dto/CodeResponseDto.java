package com.example.iworks.domain.code.dto;

import com.example.iworks.domain.code.entity.Code;
import lombok.Getter;

@Getter
public class CodeResponseDto {
    private final Integer codeId;
    private final Integer codeCode;
    private final String codeName;

    public CodeResponseDto(Code code){
        this.codeId = code.getCodeId();
        this.codeCode = code.getCodeCode();
        this.codeName = code.getCodeName();
    }
}
