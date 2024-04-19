package com.hippo.orderservice.models;

import lombok.Data;

@Data
public class OrderResponseDto {
    private Integer userId;
    private String productId;
    private Integer orderId;
    private Integer amount;
    private OrderStatus orderStatus;

}
