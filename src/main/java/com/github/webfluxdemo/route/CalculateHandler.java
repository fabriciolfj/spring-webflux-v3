package com.github.webfluxdemo.route;

import com.github.webfluxdemo.handler.CalculatorHanlder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class CalculateHandler {

    private final CalculatorHanlder calculatorHanlder;

    @Bean
    public RouterFunction<ServerResponse> pathBaseCalc() {
        return RouterFunctions.route().path("calculator", this::serverResponseRouterFunctionCalc)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunctionCalc() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"), calculatorHanlder::additionHandler)
                .GET("{a}/{b}", isOperation("*"), calculatorHanlder::multiplicationHandler)
                .GET("{a}/{b}", isOperation("-"), calculatorHanlder::subtractionHandler)
                .GET("{a}/{b}", isOperation("/"), calculatorHanlder::divisionHandler)
                .GET("{a}/{b}", req -> ServerResponse.badRequest().bodyValue("Op not found"))
                .build();
    }

    private RequestPredicate isOperation(final String operation) {
        return RequestPredicates.headers(h -> operation.equals(h.asHttpHeaders().toSingleValueMap().get("OP")));
    }
}

