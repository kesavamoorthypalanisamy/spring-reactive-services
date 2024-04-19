package com.hippo.orderservice.service;

import org.springframework.stereotype.Service;
import com.hippo.orderservice.models.OrderResponseDto;
import com.hippo.orderservice.repository.OrderRepository;
import com.hippo.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderRepository orderRepository;

    /*
    public List<Order> getOrdersOfUser(int userId) {
        return orderRepository.findByUserId(userId);
        To create Lazy function it should be inside the FLux/Mono pipeline
    }
    */
    public Flux<OrderResponseDto> getOrdersOfUser(Integer userId) {
        return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream()) // It's blocking here
                .map(EntityDtoUtil::toOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic()); //this subscribe is very importent if there is any blocking actionin the pipeline.
    }

}
