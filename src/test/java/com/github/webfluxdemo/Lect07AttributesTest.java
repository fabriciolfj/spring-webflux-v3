package com.github.webfluxdemo;

import com.github.webfluxdemo.dto.MathRequestDto;
import com.github.webfluxdemo.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lect07AttributesTest extends WebfluxDemoApplicationTests {

    @Autowired
    WebClient webClient;

    @Test
    public void testPost() {
        Mono<ResponseDto> responseDtoMono =
                webClient.post()
                        .uri("/api/v2/math")
                        .bodyValue(new MathRequestDto(10, 20))
                        .attribute("auth", "basic")
                        .retrieve()
                        .bodyToMono(ResponseDto.class)
                        .doOnNext(System.out::println);

        StepVerifier.create(responseDtoMono)
                .expectNextCount(1)
                .verifyComplete();
    }

}
