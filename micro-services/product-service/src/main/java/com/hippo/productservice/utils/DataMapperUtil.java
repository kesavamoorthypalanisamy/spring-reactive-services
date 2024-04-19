package com.hippo.productservice.utils;

import com.hippo.productservice.entity.Product;
import com.hippo.productservice.models.ProductDto;

public class DataMapperUtil {
    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setId(productDto.getId());
        product.setPrice(productDto.getPrice());
        return product;
    }
    
    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
