package com.github.webfluxdemo.webclient;

import com.github.webfluxdemo.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

public class Lect04Error extends WebfluxDemoApplicationTests {

    @Autowired
    private WebClient webClient;

    @Test
    public void testError() {
        var mono = webClient.get()
                .uri("/api/v2/math/{square}/table/monoerror", 5)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getCause()));

        StepVerifier.create(mono)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
