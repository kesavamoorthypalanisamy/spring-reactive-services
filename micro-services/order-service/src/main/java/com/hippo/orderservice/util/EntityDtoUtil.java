package com.hippo.orderservice.util;

import org.springframework.beans.BeanUtils;
import com.hippo.orderservice.entity.PurchaseOrder;
import com.hippo.orderservice.models.OrderRequestContext;
import com.hippo.orderservice.models.OrderResponseDto;
import com.hippo.orderservice.models.OrderStatus;
import com.hippo.orderservice.models.TransactionRequestDto;
import com.hippo.orderservice.models.TrasactionStatus;

public class EntityDtoUtil {
    public static OrderRequestContext toTransactionRequestDto(OrderRequestContext orderRequest) {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setUserId(orderRequest.getOrderRequestDto().getUserId());
        transactionRequestDto.setAmount(orderRequest.getProductDto().getPrice());
        orderRequest.setTrasactionRequestDto(transactionRequestDto);
        return orderRequest;
    }

    public static PurchaseOrder toOrder(OrderRequestContext orderRequest) {
        PurchaseOrder order = new PurchaseOrder();
        order.setUserId(orderRequest.getOrderRequestDto().getUserId());
        order.setProductId(orderRequest.getOrderRequestDto().getProductId());
        order.setAmount(orderRequest.getProductDto().getPrice());
        order.setOrderStatus(orderRequest.getTrasactionResponseDto().getStatus()
                .equals(TrasactionStatus.APPROVED) ? OrderStatus.COMPLETED : OrderStatus.FAILED);
        return order;
    }

    public static OrderResponseDto toOrderResponseDto(PurchaseOrder order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        BeanUtils.copyProperties(order, orderResponseDto);
        orderResponseDto.setOrderId(order.getId().intValue());
        return orderResponseDto;
    }
}
