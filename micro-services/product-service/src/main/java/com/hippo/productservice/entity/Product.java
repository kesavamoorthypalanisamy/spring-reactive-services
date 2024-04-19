package com.hippo.productservice.entity;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String description;
    private Integer price;

    public Product(String description, Integer price) {
        this.description = description;
        this.price = price;
    }
}
