package com.hippo.productservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hippo.productservice.models.ProductDto;
import com.hippo.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;




@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // @GetMapping("all")
    // public Mono<ResponseEntity<Flux<ProductDto>>> getAllProducts() {
    //     Flux<ProductDto> allProductsFlux = productService.getAllProducts();
    //     return allProductsFlux.map(i -> ResponseEntity.ok().body(allProductsFlux))
    //                     .defaultIfEmpty(ResponseEntity.notFound().build()).single();
    // }

    @GetMapping("all")
    public Flux<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable("id") String id) {
        return productService.getProductById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> addNewProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return productService.addProduct(productDtoMono);
    }

    @PutMapping
    public Mono<ResponseEntity<ProductDto>> updateExistingProduct(
            @RequestBody Mono<ProductDto> productDtoMono) {
        return productService.updateProduct(productDtoMono).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable("id") String id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("price-range")
    public Flux<ProductDto>  getProductByPriceRange(@RequestParam("min") Integer min,
            @RequestParam("max") Integer max) {
        return productService.getProductByPriceRange(min, max);
    }

}
