package com.hippo.reactiveproject;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.hippo.reactiveproject.models.CustomResponse;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class GETMultipleResponseTest extends BaseTest {
    
    @Test
    @Order(1)
    public void fluxTest() {
        Flux<CustomResponse> responseFlux = webClient.get().uri("math/reactive/table/{input}", 5)
                .retrieve().bodyToFlux(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseFlux).expectNextCount(10).verifyComplete();
    }
    @Test
    @Order(2)
    public void fluxStreamTest() {
        Flux<CustomResponse> responseFlux = webClient.get().uri("math/reactive/table/{input}/stream", 5)
                .retrieve().bodyToFlux(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseFlux).expectNextCount(10).verifyComplete();
    }
    
    
    
}
