package com.hippo.reactiveproject;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import com.hippo.reactiveproject.models.CustomResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BadRequestTest extends BaseTest {

    @Test
    public void badRequestTest() {
        Mono<CustomResponse> responseMono = webClient.get().uri("math/valid/reactive/square/{input}", 5)
                .retrieve().bodyToMono(CustomResponse.class)
                .doOnError(err -> System.out.println(err.getMessage()))
                .doOnNext(System.out::println);
        StepVerifier.create(responseMono).verifyError(BadRequest.class);
    }
}
