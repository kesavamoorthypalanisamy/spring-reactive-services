package com.hippo.orderservice.entity;

import com.hippo.orderservice.models.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "purchase_order")
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private Long id;
    private String productId;
    private Integer userId;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
