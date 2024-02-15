package com.example.iworks.global.exception.dto.response;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
public class ValidationExceptionResponseDto {

    private String message;

    public static ResponseEntity<ValidationExceptionResponseDto> toResponse(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ValidationExceptionResponseDto.builder()
                        .message(Objects.requireNonNull(
                                e.getBindingResult().getFieldError()
                        ).getDefaultMessage())
                        .build()
                );
    }
}
