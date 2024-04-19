package com.hippo.orderservice.models;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserTransactionsDto {
    private Integer amount;
    private LocalDateTime transactionDate;
}
