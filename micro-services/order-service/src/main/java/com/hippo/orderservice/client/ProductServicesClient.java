package com.hippo.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.hippo.orderservice.models.ProductDto;
import reactor.core.publisher.Mono;

@Service
public class ProductServicesClient {
    private final WebClient webClient;

    public ProductServicesClient(@Value("${product.service.url}") String urlString) {
        this.webClient = WebClient.builder().baseUrl(urlString).build();
    }

    public Mono<ProductDto> getProductById(final String prodcutId) {
        return webClient.get().uri("{id}", prodcutId).retrieve().bodyToMono(ProductDto.class);
    }

}
