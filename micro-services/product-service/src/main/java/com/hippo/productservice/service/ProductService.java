package com.hippo.productservice.service;

import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import com.hippo.productservice.entity.Product;
import com.hippo.productservice.models.ProductDto;
import com.hippo.productservice.repository.ProductRepository;
import com.hippo.productservice.utils.DataMapperUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<ProductDto> getAllProducts() {
        Flux<Product> allProducts = productRepository.findAll();
        return allProducts.map(DataMapperUtil::entityToDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id).map(DataMapperUtil::entityToDto);
    }
    
    public Mono<ProductDto> addProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono.map(DataMapperUtil::dtoToEntity).flatMap(productRepository::insert)
                .map(DataMapperUtil::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .flatMap(dto -> productRepository.findById(dto.getId())
                        .map(p -> DataMapperUtil.dtoToEntity(dto)).flatMap(productRepository::save))
                .map(DataMapperUtil::entityToDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

    public Flux<ProductDto> getProductByPriceRange(Integer min, Integer max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(DataMapperUtil::entityToDto);
    }

}
