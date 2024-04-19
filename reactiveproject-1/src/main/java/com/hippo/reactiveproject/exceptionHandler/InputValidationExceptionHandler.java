package com.hippo.reactiveproject.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.hippo.reactiveproject.exceptions.InputValidationException;
import com.hippo.reactiveproject.models.InputFailedValidationResponse;

@ControllerAdvice
public class InputValidationExceptionHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException ex) {
        InputFailedValidationResponse inputFailedValidationResponse =
                new InputFailedValidationResponse();
        inputFailedValidationResponse.setErrorCode(ex.getErrorCode());
        inputFailedValidationResponse.setMessage(ex.getMessage());
        inputFailedValidationResponse.setInput(ex.getInput());
        return ResponseEntity.badRequest().body(inputFailedValidationResponse);

    }
}
