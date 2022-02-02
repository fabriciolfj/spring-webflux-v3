package com.github.webfluxdemo.exceptionhandler;

import com.github.webfluxdemo.dto.InputFailedValidationResponse;
import com.github.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleInput(final InputValidationException ex) {
        var response = InputFailedValidationResponse.builder()
                .input(ex.getInput())
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);

    }
}
