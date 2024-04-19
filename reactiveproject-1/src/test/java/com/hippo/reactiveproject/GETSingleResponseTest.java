package com.hippo.reactiveproject;

import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import com.hippo.reactiveproject.models.CustomResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GETSingleResponseTest extends BaseTest {

    @Test
    @Order(1)
    public void blockTest() {
        CustomResponse response = webClient.get().uri("math/reactive/square/{input}", 5).retrieve()
                .bodyToMono(CustomResponse.class) //Mono<CustomResponse>
                .block();
        System.out.println(response);
    }
    
    @Test
    @Order(2)
    public void stepVerifierTest() {
        Mono<CustomResponse> responseMono = webClient.get().uri("math/reactive/square/{input}", 5)
                .retrieve().bodyToMono(CustomResponse.class); //Mono<CustomResponse>
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 25)
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void queryParamsTest1() {
        URI uri = UriComponentsBuilder.fromUriString("math/reactive/square?input={input}").build(5);
        Mono<CustomResponse> responseMono = webClient.get().uri(uri.toString()).retrieve()
                .bodyToMono(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 25)
                .verifyComplete();
    }
    @Test
    @Order(4)
    public void queryParamsTest2() {
        Mono<CustomResponse> responseMono = webClient.get()
                .uri(b -> b.path("math/reactive/square").query("input={input}").build(5)).retrieve()
                .bodyToMono(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 25)
                .verifyComplete();
    }
    
    /*
     * This is very helpful if we need to pass many query params
     */
    @Test
    @Order(5)
    public void queryParamsTest3() {
        Map<String, Integer> queryParams = Map.of("input", 5);
        Mono<CustomResponse> responseMono = webClient.get()
                .uri(b -> b.path("math/reactive/square").query("input={input}").build(queryParams)).retrieve()
                .bodyToMono(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 25)
                .verifyComplete();
    }
    
}
