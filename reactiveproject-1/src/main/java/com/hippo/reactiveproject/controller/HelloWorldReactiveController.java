package com.hippo.reactiveproject.controller;

import java.time.Duration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;


@RestController
public class HelloWorldReactiveController {
   
    @GetMapping("/hello")
    public Mono<String> greetings() {
        Mono<String> computeMessage = greetingMessage();
        Mono<String> nameFromDb = getNameFromDb();
        Mono<Tuple2<String, String>> zipWith = computeMessage.zipWith(nameFromDb);
        return zipWith.map(value -> value.getT1()+value.getT2());
    }

    private Mono<String> greetingMessage() {
        return Mono.just("Hello").delayElement(Duration.ofSeconds(5));
    }

    private Mono<String> getNameFromDb() {
        return Mono.just(" kesava").delayElement(Duration.ofSeconds(5));
    }

}
