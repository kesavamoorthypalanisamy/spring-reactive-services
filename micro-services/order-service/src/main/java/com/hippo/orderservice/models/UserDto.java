package com.hippo.orderservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private Integer balance;

    public UserDto(String name, Integer balance) {
        this.name = name;
        this.balance = balance;
    }
}
