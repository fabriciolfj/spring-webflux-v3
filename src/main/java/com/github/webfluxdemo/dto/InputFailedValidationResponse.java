package com.github.webfluxdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputFailedValidationResponse {

    private int errorCode;
    private int input;
    private String message;
}
