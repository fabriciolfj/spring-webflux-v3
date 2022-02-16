package com.github.webfluxdemo;

import com.github.webfluxdemo.dto.MathRequestDto;
import com.github.webfluxdemo.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lect03PostRequestTest extends WebfluxDemoApplicationTests {

    @Autowired
    private WebClient webClient;

    @Test
    public void testMath() {
        var mono = webClient.post()
                .uri("/api/v2/math")
                .bodyValue(new MathRequestDto(4, 4))
                .header("key", "value")
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
