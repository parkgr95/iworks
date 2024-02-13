package com.example.iworks.global.exception;

import com.example.iworks.global.exception.dto.response.GlobalExceptionResponseDto;
import com.example.iworks.global.exception.dto.response.ValidationExceptionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalExceptionResponseDto> globalHandlerException(GlobalException e) {
        return GlobalExceptionResponseDto.toResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto> validationHandlerException(MethodArgumentNotValidException e) {
        return ValidationExceptionResponseDto.toResponse(e);
    }

}
