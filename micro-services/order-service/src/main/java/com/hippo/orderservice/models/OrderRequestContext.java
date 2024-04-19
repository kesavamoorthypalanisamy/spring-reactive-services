package com.hippo.orderservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequestContext {
    private OrderRequestDto orderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto trasactionRequestDto;
    private TransactionResponseDto trasactionResponseDto;
   
    public OrderRequestContext(OrderRequestDto orderRequestDto) {
        this.orderRequestDto = orderRequestDto;
    }

}
