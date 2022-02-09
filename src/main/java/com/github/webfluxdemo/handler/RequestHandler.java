package com.github.webfluxdemo.handler;

import com.github.webfluxdemo.dto.MathRequestDto;
import com.github.webfluxdemo.dto.ResponseDto;
import com.github.webfluxdemo.exception.InputValidationException;
import com.github.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(final ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));

        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }

        return Mono.just(input)
                .map(v -> mathService.findSquare(v))
                .flatMap(resp -> ServerResponse.ok().body(resp, ResponseDto.class));
    }

    public Mono<ServerResponse> tableHandler(final ServerRequest serverRequest) {
        return Mono.just(Integer.parseInt(serverRequest.pathVariable("input")))
                .map(v -> mathService.findTableSquare(v))
                .flatMap(v -> ServerResponse.ok().body(v, ResponseDto.class));
    }

    public Mono<ServerResponse> tableStreamHandler(final ServerRequest serverRequest) {
        return Mono.just(Integer.parseInt(serverRequest.pathVariable("input")))
                .map(v -> mathService.findTableSquare(v))
                .flatMap(t -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(t, ResponseDto.class));
    }

    public Mono<ServerResponse> multiply(final ServerRequest serverRequest) {
        return serverRequest.bodyToMono(MathRequestDto.class)
                .map(value -> Mono.just(value))
                .map(v -> mathService.tableCalculate(v))
                .flatMap(result -> ServerResponse.ok().body(result, ResponseDto.class));
    }
}
