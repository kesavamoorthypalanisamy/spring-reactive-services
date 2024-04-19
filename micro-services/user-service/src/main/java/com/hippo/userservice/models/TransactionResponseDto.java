package com.hippo.userservice.models;

import lombok.Data;

@Data
public class TransactionResponseDto {
    private Integer userId;
    private Integer amount;
    private TrasactionStatus status;
}
