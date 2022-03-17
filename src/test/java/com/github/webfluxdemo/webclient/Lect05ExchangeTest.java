package com.github.webfluxdemo.webclient;

import com.github.webfluxdemo.dto.InputFailedValidationResponse;
import com.github.webfluxdemo.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lect05ExchangeTest extends WebfluxDemoApplicationTests {

    @Autowired
    private WebClient webClient;

    @Test
    public void testError() {
        var mono = webClient.get()
                .uri("/api/v2/math/{square}/table/monoerror", 5)
                .exchangeToMono(e -> {
                    if (e.statusCode().value() == 400) {
                        return e.bodyToMono(InputFailedValidationResponse.class);
                    }

                    return e.bodyToMono(ResponseDto.class);
                })
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getCause()));

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();

    }
}
