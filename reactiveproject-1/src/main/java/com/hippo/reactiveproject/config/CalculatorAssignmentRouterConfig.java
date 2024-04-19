package com.hippo.reactiveproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.hippo.reactiveproject.service.Calculatorhandler;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CalculatorAssignmentRouterConfig {
    private final Calculatorhandler calculatorhandler;

   @Bean
   public RouterFunction<ServerResponse> serverResponseRouterFunction() {
       return RouterFunctions.route()
               .GET("calculator/{a}/{b}",
                       RequestPredicates.headers(
                               h -> "+".equals(h.asHttpHeaders().toSingleValueMap().get("OP"))),
                       calculatorhandler::handleAddition)
               .GET("calculator/{a}/{b}", isOperation("-"), calculatorhandler::handleSubtraction)
               .GET("calculator/{a}/{b}", isOperation("*"), calculatorhandler::handleMultiplication)
               .GET("calculator/{a}/{b}", isOperation("/"), calculatorhandler::handleDivision)
               .GET("calculator/{a}/{b}",
                       req -> ServerResponse.badRequest().bodyValue("OP should be + - * /"))
               .build();
   }
    
   private RequestPredicate isOperation(String operation) {
       return RequestPredicates.headers(
               header -> operation.equals(header.asHttpHeaders().toSingleValueMap().get("OP")));
   }
}
