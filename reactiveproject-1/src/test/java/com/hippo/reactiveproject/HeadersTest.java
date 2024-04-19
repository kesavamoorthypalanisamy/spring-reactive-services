package com.hippo.reactiveproject;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.models.MultiplyRequestDto;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class HeadersTest extends BaseTest{
    @Test
    @Order(1)
    public void headersTest() {
        Mono<CustomResponse> responseMono = webClient.post().uri("math/reactive/multiply")
                .bodyValue(MultiplyRequestDto.builder().first(1).second(3).build())
                .headers(h -> h.set("someKey", "someValue")).retrieve()
                .bodyToMono(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 3)
                .verifyComplete();

    }

    @Test
    @Order(2)
    public void basicAuthHeadersTest() {
        Mono<CustomResponse> responseMono = webClient.post().uri("math/reactive/multiply")
                .bodyValue(MultiplyRequestDto.builder().first(1).second(3).build())
                .headers(h -> h.setBasicAuth("userName", "somePassword"))
                .retrieve()
                .bodyToMono(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 3)
                .verifyComplete();

    }
}
