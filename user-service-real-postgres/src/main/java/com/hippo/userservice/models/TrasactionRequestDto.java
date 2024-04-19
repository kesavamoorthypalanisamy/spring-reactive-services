package com.hippo.userservice.models;

import lombok.Data;

@Data
public class TrasactionRequestDto {
    private Integer userId;
    private Integer amount;
}
