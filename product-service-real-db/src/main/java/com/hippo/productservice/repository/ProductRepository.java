package com.hippo.productservice.repository;

import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.hippo.productservice.entity.Product;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    /*
     *  > min & max < 
     */
    // Flux<Product> findByPriceBetween(Integer min, Integer max);

    /*
     * => min and max <= 
     */
    Flux<Product> findByPriceBetween(Range<Integer> range);
    
}
