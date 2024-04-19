package com.hippo.reactiveproject;

import org.junit.jupiter.api.Test;
import com.hippo.reactiveproject.models.CustomResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CalculatorAssignment extends BaseTest {
    private static final Integer A = 10;
    private static final String outputFormat = "%d %s %d = %s";
    
    /*
     * Assignment challange:
     * A will be 10 always b -> 1 to 5
     * operations +,-,*,/ 
     * need to print value for each b value and operation in specific format below
     * a+b=result ex: 10+1=11
     * 
     * invoke calculator/{a}/{b}
     * 
     */
    @Test
    public void test() {
        Flux<String> resultFlux = Flux.range(1, 5)
                .flatMap(b -> Flux.just("+", "-", "*", "/")
                        .flatMap(op -> send(b, op)))
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));
        StepVerifier.create(resultFlux).expectNextCount(20).verifyComplete();
    }

    private Mono<String> send(Integer b, String op) {
        return webClient.get()
                .uri("calculator/{a}/{b}",A,b)
                .header("OP", op)
                .retrieve()
                .bodyToMono(CustomResponse.class)
                .map(v -> String.format(outputFormat, A, op, b, v.getOutputValue()));
    }
    

}
