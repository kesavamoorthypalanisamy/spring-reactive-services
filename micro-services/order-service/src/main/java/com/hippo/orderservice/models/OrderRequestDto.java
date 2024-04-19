package com.hippo.orderservice.models;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Integer userId;
    private String productId;
}
