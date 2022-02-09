package com.github.webfluxdemo.route;

import com.github.webfluxdemo.handler.CalculatorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class CalculateHandler {

    private final CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> pathBaseCalc() {
        return RouterFunctions.route().path("calculator", this::serverResponseRouterFunctionCalc)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunctionCalc() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"), calculatorHandler::additionHandler)
                .GET("{a}/{b}", isOperation("*"), calculatorHandler::multiplicationHandler)
                .GET("{a}/{b}", isOperation("-"), calculatorHandler::subtractionHandler)
                .GET("{a}/{b}", isOperation("/"), calculatorHandler::divisionHandler)
                .GET("{a}/{b}", req -> ServerResponse.badRequest().bodyValue("Op not found"))
                .build();
    }

    private RequestPredicate isOperation(final String operation) {
        return RequestPredicates.headers(h -> operation.equals(h.asHttpHeaders().toSingleValueMap().get("OP")));
    }
}

