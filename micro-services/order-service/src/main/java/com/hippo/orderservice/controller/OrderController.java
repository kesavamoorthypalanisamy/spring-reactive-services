package com.hippo.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.hippo.orderservice.models.OrderRequestDto;
import com.hippo.orderservice.models.OrderResponseDto;
import com.hippo.orderservice.service.OrderFulfilmentService;
import com.hippo.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderFulfilmentService orderFulfilmentService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    public Mono<ResponseEntity<OrderResponseDto>> createOrder(
            @RequestBody Mono<OrderRequestDto> requestMono) {
        return orderFulfilmentService.processOrder(requestMono).map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class,
                        ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class,
                        ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("{userId}")
    public Flux<OrderResponseDto> getOrdersByUserId(@PathVariable("userId") Integer userId) {
        return orderQueryService.getOrdersOfUser(userId);
    }

}
