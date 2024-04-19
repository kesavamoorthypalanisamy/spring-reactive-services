package com.hippo.reactiveproject.models;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class InputFailedValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
