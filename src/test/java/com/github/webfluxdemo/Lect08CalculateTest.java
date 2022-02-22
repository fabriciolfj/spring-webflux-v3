package com.github.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lect08CalculateTest extends WebfluxDemoApplicationTests {

    private static final String FORMAT = "%d %s %d = %s";
    private static final int A = 10;

    @Autowired
    private WebClient webClient;

    @Test
    public void testCalculate() {
        /*final var result = send(5, "*");
        System.out.println(result);*/

        final var flux = Flux.range(1, 5)
                .flatMap(v -> Flux.just("*", "-", "+", "/")
                        .flatMap(op -> send(v, op)))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Mono<String> send(int b, String op) {
        return webClient.get()
                .uri("calculator/" + A + "/" + b)
                .headers(h -> h.set("OP", op))
                .retrieve()
                .bodyToMono(String.class)
                .map(v -> String.format(FORMAT, A, op, b, v));
    }
}
