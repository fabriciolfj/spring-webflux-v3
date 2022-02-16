package com.github.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class Lect06QueryParamsTest extends WebfluxDemoApplicationTests {

    @Autowired
    private WebClient webClient;

    Map<String, Integer> params = Map.of(
            "count", 10,
            "page" ,  20
    );

    @Test
    public void queryParamsTest(){

        Flux<Integer> flux = this.webClient
                .get()
                .uri(b ->  b.path("jobs").query("count={count}&page={page}").build(params))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
