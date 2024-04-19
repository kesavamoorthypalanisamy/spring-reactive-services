package com.hippo.reactiveproject.service;

import java.time.Duration;
import org.springframework.stereotype.Service;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.models.MultiplyRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MyReactiveMathService {
    public Mono<CustomResponse> findSqure(Integer input) {
        return Mono.fromSupplier(() -> input * input).map(CustomResponse::new);
    }
    
    public Flux<CustomResponse> multiplicationTable(Integer input) {
        return Flux.range(0, 10).delayElements(Duration.ofSeconds(1))
                // .doOnNext(i -> SleepUtil.sleepInSeconds(1)) //This is blocking way of implementation
                .doOnNext(i -> System.out.println("reactive MathService processing: " + i))
                .map(i -> new CustomResponse(i * input));

        //Below implementation is not reactive. Inthis implementation all the logic executed outside the pipeline.
        /*Flux.fromIterable(IntStream.range(0, 10)
                .peek(i -> SleepUtil.sleepInSeconds(1))
                .peek(i -> System.out.println("MathService processing: "+i))
                .mapToObj(i -> new CustomResponse(i * input))
                .toList());
        */
    }

    public Mono<CustomResponse> multiplyTwoNumbers(Mono<MultiplyRequestDto> request) {
        return request.map(dto -> dto.getFirst() * dto.getSecond()).map(CustomResponse::new);
    }
    

}
