package com.hippo.reactiveproject.service;

import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class Calculatorhandler {
    public Mono<ServerResponse> handleAddition(ServerRequest request) {
        return calculate(request, (a, b) ->ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> handleSubtraction(ServerRequest request) {
        return calculate(request, (a, b) ->ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> handleMultiplication(ServerRequest request) {
        return calculate(request, (a, b) ->ServerResponse.ok().bodyValue(a * b));
    }
    public Mono<ServerResponse> handleDivision(ServerRequest request) {
        return calculate(request, (a, b) ->{
            if(b != 0){
                return ServerResponse.ok().bodyValue(a/b);
            } else {
                return ServerResponse.badRequest().bodyValue("b can not be 0");
            }
        });
      }

    private Mono<ServerResponse> calculate(ServerRequest request,
            BiFunction<Integer, Integer, Mono<ServerResponse>> function) {
        int a = Integer.parseInt(request.pathVariable("a"));
        int b = Integer.parseInt(request.pathVariable("b"));
        return function.apply(a, b);
    }
    
}
