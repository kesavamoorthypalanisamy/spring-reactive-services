package com.hippo.orderservice.service;

import org.springframework.stereotype.Service;
import com.hippo.orderservice.client.ProductServicesClient;
import com.hippo.orderservice.client.UserServiceClient;
import com.hippo.orderservice.models.OrderRequestContext;
import com.hippo.orderservice.models.OrderRequestDto;
import com.hippo.orderservice.models.OrderResponseDto;
import com.hippo.orderservice.repository.OrderRepository;
import com.hippo.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderFulfilmentService {
    private final UserServiceClient userServiceClient;
    private final ProductServicesClient productServicesClient;
    private final OrderRepository orderRepository;

    /*
     * we have to execute in this sequence.
     * 1. we have to get product information from Product service
     * 2. we have to ceate transaction in user service
     * 3. based on the User service response we have to record in the Orer service DB
     * 4. then we need to respond back to user
     * 
     */
    public Mono<OrderResponseDto> processOrder(Mono<OrderRequestDto> requestMono) {
        return requestMono.map(r -> new OrderRequestContext(r)).flatMap(this::productRequest)
                .doOnNext(EntityDtoUtil::toTransactionRequestDto).doOnNext(System.out::println)
                .flatMap(this::doTransaction).map(EntityDtoUtil::toOrder).map(orderRepository::save) //Blocking DB call
                .map(EntityDtoUtil::toOrderResponseDto).subscribeOn(Schedulers.boundedElastic()); //this subscribe is very importent if there is any blocking actionin the pipeline.
        //Explore about publishOn() use also.
    }
    
    private Mono<OrderRequestContext> productRequest(OrderRequestContext r) {
        return productServicesClient.getProductById(r.getOrderRequestDto().getProductId())
                .doOnNext(p -> r.setProductDto(p)).thenReturn(r);
    }

    private Mono<OrderRequestContext> doTransaction(OrderRequestContext r) {
        return userServiceClient.createTransaction(r.getTrasactionRequestDto())
                .doOnNext(r::setTrasactionResponseDto).thenReturn(r);

    }

    
}
