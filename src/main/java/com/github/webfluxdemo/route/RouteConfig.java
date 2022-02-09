package com.github.webfluxdemo.route;

import com.github.webfluxdemo.dto.InputFailedValidationResponse;
import com.github.webfluxdemo.exception.InputValidationException;
import com.github.webfluxdemo.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.RouteMatcher;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> pathBase() {
        return RouterFunctions.route().path("router", this::serverResponseRouterFunction)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.GET("*/1?"), requestHandler::squareHandler)
                .GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("Value between 11 and 19"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("stream/{input}", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiply)
                .onError(InputValidationException.class, handleException())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> handleException() {
        return (err, request) -> {
            var exception = (InputValidationException) err;
            var response = InputFailedValidationResponse.builder()
                    .message(exception.getMessage())
                    .errorCode(exception.getErrorCode())
                    .input(exception.getInput())
                    .build();

            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
