package com.github.webfluxdemo.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    public Mono<ServerResponse> additionHandler(final ServerRequest request) {
        return processo(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> subtractionHandler(final ServerRequest request) {
        return processo(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplicationHandler(final ServerRequest request) {
        return processo(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(final ServerRequest request) {
        return processo(request, (a, b) -> b != 0 ? ServerResponse.ok().bodyValue(a / b) : ServerResponse.badRequest().bodyValue("B not valid"));
    }

    private Mono<ServerResponse> processo(final ServerRequest request, final BiFunction<Integer, Integer, Mono<ServerResponse>> logic) {
        int a = getValue(request, "a");
        int b = getValue(request, "b");

        return logic.apply(a, b);
    }

    private int getValue(final ServerRequest request, final String key) {
        return Integer.parseInt(request.pathVariable(key));
    }
}
