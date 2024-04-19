package com.hippo.reactiveproject;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import com.hippo.reactiveproject.exceptions.InputValidationException;
import com.hippo.reactiveproject.models.CustomResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ExchangeTest extends BaseTest {
   // Exchnage = reterive + additional info http status code
    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = webClient.get()
                .uri("math/valid/reactive/square/{input}", 1)
                .exchangeToMono(this::exchange)
                .doOnError(err -> System.out.println(err.getMessage()))
                .doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextCount(1).verifyComplete();
    }
    
    private Mono<Object> exchange(ClientResponse cr) {
        if (cr.statusCode().value() == 400) {
            return cr.bodyToMono(InputValidationException.class);
        }
        return cr.bodyToMono(CustomResponse.class);
    }

}
