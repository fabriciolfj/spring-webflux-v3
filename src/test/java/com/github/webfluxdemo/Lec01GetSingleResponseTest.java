package com.github.webfluxdemo;

import com.github.webfluxdemo.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends WebfluxDemoApplicationTests {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        var response = this.webClient
                .get()
                .uri("/api/v2/math/{square}", 5)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();

        System.out.println(response);
    }

    @Test
    public void stepVerifier() {
        var mono = this.webClient
                .get()
                .uri("/api/v2/math/{square}", 5)
                .retrieve()
                .bodyToMono(ResponseDto.class);

        StepVerifier.create(mono)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();
    }
}
