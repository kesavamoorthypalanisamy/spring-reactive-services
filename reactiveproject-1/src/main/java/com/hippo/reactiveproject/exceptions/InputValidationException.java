package com.hippo.reactiveproject.exceptions;

import lombok.Getter;

public class InputValidationException extends RuntimeException{
    private static final String MSG = "Allowd range is 10 - 20";
    @Getter
    private int errorCode;
    @Getter
    private int input;

    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
        this.errorCode = 100;
    }
    public InputValidationException() {
        super(MSG);
        this.errorCode = 100;
    }
    
}
