package com.hippo.reactiveproject.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.hippo.reactiveproject.exceptions.InputValidationException;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.models.MultiplyRequestDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RouteRequestHandler {
    private final MyReactiveMathService mathService;

    public Mono<ServerResponse> handleSquare(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<CustomResponse> squreResponse = mathService.findSqure(input);
        return ServerResponse.ok().body(squreResponse, CustomResponse.class);
    }

    public Mono<ServerResponse> handleMultiplyTable(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<CustomResponse> multiplicationTableResponse = mathService.multiplicationTable(input);
        return ServerResponse.ok().body(multiplicationTableResponse, CustomResponse.class);
    }

    public Mono<ServerResponse> handleMultiplyTableStream(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<CustomResponse> multiplicationTableResponse = mathService.multiplicationTable(input);
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(multiplicationTableResponse, CustomResponse.class);

    }

    public Mono<ServerResponse> multiplyPost(ServerRequest request) {
        Mono<MultiplyRequestDto> bodyMono = request.bodyToMono(MultiplyRequestDto.class);
        Mono<CustomResponse> multiplyTwoNumbers = mathService.multiplyTwoNumbers(bodyMono);
        return ServerResponse.ok().body(multiplyTwoNumbers, CustomResponse.class);
    }
    public Mono<ServerResponse> handleSquareWithException(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        if (input > 9 && input < 21) {
            Mono<CustomResponse> squreResponse = mathService.findSqure(input);
            return ServerResponse.ok().body(squreResponse, CustomResponse.class);
        } else {
            // return ServerResponse.badRequest().bodyValue(new InputValidationException(input));
            return Mono.error(new InputValidationException(input)); // Emitting the error signal instead of throwing simply
        }
    }
}
