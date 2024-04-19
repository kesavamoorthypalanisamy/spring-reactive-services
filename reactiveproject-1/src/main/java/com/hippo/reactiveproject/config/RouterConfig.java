package com.hippo.reactiveproject.config;

import java.util.function.BiFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.hippo.reactiveproject.exceptions.InputValidationException;
import com.hippo.reactiveproject.models.InputFailedValidationResponse;
import com.hippo.reactiveproject.service.RouteRequestHandler;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final RouteRequestHandler handler;
    
    @Bean
    public RouterFunction<ServerResponse> highLevelRouterFunction() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                // .path("router1", this::serverResponseRouterFunction1)
                .build();
    }

    //if this is invoked by highlevel function like this no need to ba a bean and public scope
    // @Bean
    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", t -> handler.handleSquare(t))
                .GET("table/{input}", handler::handleMultiplyTable)
                .GET("table/{input}/stream", handler::handleMultiplyTableStream)
                .POST("multiply", handler::multiplyPost)
                .GET("square/{input}/validation", handler::handleSquareWithException)
                .onError(InputValidationException.class, validationExceptionhamndler()).build();
    }
    
    /*/
    public RouterFunction<ServerResponse> serverResponseRouterFunction1() {
        return RouterFunctions.route().GET("router/square/{input}", t -> handler.handleSquare(t))
                .GET("router1/table/{input}", handler::handleMultiplyTable)
                .GET("router1/table/{input}/stream", handler::handleMultiplyTableStream)
                .POST("router1/multiply", handler::multiplyPost)
                .GET("router1/square/{input}/validation", handler::handleSquareWithException)
                .onError(InputValidationException.class, validationExceptionhamndler())
                .build();
    }*/

    private BiFunction<InputValidationException, ServerRequest, Mono<ServerResponse>> validationExceptionhamndler() {
        return (ex, req) -> {
        InputFailedValidationResponse inputFailedValidationResponse =
                new InputFailedValidationResponse();
        inputFailedValidationResponse.setErrorCode(ex.getErrorCode());
        inputFailedValidationResponse.setMessage(ex.getMessage());
        inputFailedValidationResponse.setInput(ex.getInput());
        return ServerResponse.badRequest().bodyValue(inputFailedValidationResponse);
        };
    }


}
