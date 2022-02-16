package com.github.webfluxdemo;

import com.github.webfluxdemo.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lect02GetMultiResponseTest extends WebfluxDemoApplicationTests {

    @Autowired
    private WebClient webClient;

    @Test
    public void testTableSquare() {
        var flux = webClient.get()
                .uri("/api/v2/math/{square}/table", 5)
                .retrieve()
                .bodyToFlux(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
