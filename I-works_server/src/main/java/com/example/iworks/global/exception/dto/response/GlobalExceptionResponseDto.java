package com.example.iworks.global.exception.dto.response;

import com.example.iworks.global.exception.GlobalException;
import lombok.*;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
public class GlobalExceptionResponseDto {

    private String result;
    private String message;

    public static ResponseEntity<GlobalExceptionResponseDto> toResponse(GlobalException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(GlobalExceptionResponseDto.builder()
                        .result(e.getResult())
                        .message(e.getMessage())
                        .build()
                );
    }

}
