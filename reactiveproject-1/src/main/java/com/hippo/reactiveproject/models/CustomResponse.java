package com.hippo.reactiveproject.models;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomResponse {
    private LocalDateTime time;
    private Integer outputValue;

    public CustomResponse(Integer outputValue) {
        this.outputValue = outputValue;
        this.time = LocalDateTime.now();
    }
}
