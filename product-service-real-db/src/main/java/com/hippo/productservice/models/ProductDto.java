package com.hippo.productservice.models;

import lombok.Data;

@Data
public class ProductDto {
    private String id;
    private String description;
    private Integer price;
}
