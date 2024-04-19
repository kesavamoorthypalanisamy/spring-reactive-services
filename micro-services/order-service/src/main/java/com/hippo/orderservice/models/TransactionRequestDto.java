package com.hippo.orderservice.models;

import lombok.Data;

@Data
public class TransactionRequestDto {
    private Integer userId;
    private Integer amount;
}
