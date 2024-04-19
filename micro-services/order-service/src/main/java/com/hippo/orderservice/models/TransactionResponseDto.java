package com.hippo.orderservice.models;

import lombok.Data;

@Data
public class TransactionResponseDto {
    private Integer userId;
    private Integer amount;
    private TrasactionStatus status;
}
