package com.hippo.reactiveproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hippo.reactiveproject.exceptions.InputValidationException;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.service.MyReactiveMathService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("math/valid/reactive/")
@RequiredArgsConstructor
public class ReactiveMathValidationContoller {
    private final MyReactiveMathService mathService;
    
    @GetMapping("square/{input}")
    public Mono<CustomResponse> getSquareValue(@PathVariable Integer input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return mathService.findSqure(input);
    }
    
    @GetMapping("square/{input}/monoError")
    public Mono<CustomResponse> getSquareValueMonoError(@PathVariable Integer input) {
        return Mono.just(input).handle((i, sink) -> {
            if (i < 10 || i > 20) {
                sink.error(new InputValidationException(i));
            } else {
                sink.next(i);
            }
        }).cast(Integer.class).flatMap(i -> mathService.findSqure(i));
    }

    @GetMapping("square/{input}/assignment")
    public Mono<ResponseEntity<CustomResponse>> assignment(@PathVariable Integer input) {
        return Mono.just(input)
                .filter(i -> i>=10 && i<=20)
                .flatMap(mathService::findSqure)
                .map(i -> ResponseEntity.ok(i))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
       
    }

}
