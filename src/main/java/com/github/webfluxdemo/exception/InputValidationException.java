package com.github.webfluxdemo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InputValidationException extends RuntimeException {

    private static final String MSG = "allowed range is 10 - 20";
    private static final int errorCode = 100;
    private int input;

    public InputValidationException(final int input) {
        super(MSG);
        this.input = input;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getInput() {
        return input;
    }
}
