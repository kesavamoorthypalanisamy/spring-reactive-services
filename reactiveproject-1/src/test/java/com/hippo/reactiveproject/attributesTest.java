package com.hippo.reactiveproject;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.models.MultiplyRequestDto;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class attributesTest extends BaseTest{
    @Test
    @Order(1)
    public void basicAttributeAuthHeadersTest() {
        Mono<CustomResponse> responseMono = webClient.post().uri("math/reactive/multiply")
                .bodyValue(MultiplyRequestDto.builder().first(1).second(3).build())
                .attribute("auth", "basic").retrieve().bodyToMono(CustomResponse.class)
                .doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 3)
                .verifyComplete();

    }
    
    @Test
    @Order(2)
    public void barrerAttributeAuthHeadersTest() {
            Mono<CustomResponse> responseMono = webClient.post().uri("math/reactive/multiply")
                            .bodyValue(MultiplyRequestDto.builder().first(1).second(3).build())
                            .attribute("auth", "oAuth").retrieve().bodyToMono(CustomResponse.class)
                            .doOnNext(System.out::println);

            StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 3)
                            .verifyComplete();

    }
    
    @Test
    @Order(3)
    public void noAuthAttributeTest() {
        Mono<CustomResponse> responseMono = webClient.post().uri("math/reactive/multiply")
                .bodyValue(MultiplyRequestDto.builder().first(1).second(3).build())
                .retrieve().bodyToMono(CustomResponse.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 3)
                .verifyComplete();

    }
    
}
