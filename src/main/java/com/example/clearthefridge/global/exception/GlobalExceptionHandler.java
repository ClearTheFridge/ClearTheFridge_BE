package com.example.clearthefridge.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode code = ex.getErrorCode();
        log.error("CustomException: {}", code.getMessage());

        return ResponseEntity
                .status(code.getStatus())
                .body(ErrorResponse.builder()
                        .status(code.getStatus().value())
                        .error(code.name())
                        .message(code.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("Validation error: {}", message);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .status(400)
                        .error("INVALID_INPUT_VALUE")
                        .message(message)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unexpected error", ex);

        return ResponseEntity
                .status(500)
                .body(ErrorResponse.builder()
                        .status(500)
                        .error("INTERNAL_SERVER_ERROR")
                        .message("예기치 않은 오류가 발생했습니다.")
                        .build());
    }
}
