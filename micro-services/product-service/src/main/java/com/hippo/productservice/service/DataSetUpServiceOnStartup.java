package com.hippo.productservice.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.hippo.productservice.entity.Product;
import com.hippo.productservice.repository.ProductRepository;
import com.hippo.productservice.utils.DataMapperUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class DataSetUpServiceOnStartup implements CommandLineRunner {
    private final ProductRepository productRepository;
    // private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        Product product1 = new Product("endurance RoadBike Aluminium shimano tiagra", 1200);
        Product product2 = new Product("Mountain bike hardtail Fox sram Eagle 1x12", 1500);
        Product product3 = new Product("Trekking bike Rolhoff and son dynamo", 2200);
        Product product4 = new Product("Trekking bike Pinion and son dynamo", 3200);

        Flux.just(product1, product2, product3, product4).flatMap(productRepository::insert)
                .map(DataMapperUtil::entityToDto).subscribe(System.out::println);
    }

    // @Override
    // public void run(String... args) throws Exception {
    //     ProductDto p1 = new ProductDto("endurance RoadBike Aluminium shimano tiagra", 1200);
    //     ProductDto p2 = new ProductDto("Mountain bike hardtail Fox sram Eagle 1x12", 1500);
    //     ProductDto p3 = new ProductDto("Trekking bike Rolhoff and son dynamo", 2200);
    //     ProductDto p4 = new ProductDto("Trekking bike Pinion and son dynamo", 3200);

    //     Flux.just(p1, p2, p3, p4).flatMap(p -> productService.addProduct(Mono.just(p)))
    //             .subscribe(System.out::println);
    // }

}
