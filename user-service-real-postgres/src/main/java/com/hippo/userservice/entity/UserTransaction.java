package com.hippo.userservice.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class UserTransaction {
    @Id
    private Integer id;
    private Integer userId;
    private Integer amount;
    private LocalDateTime transactionDate;
}
