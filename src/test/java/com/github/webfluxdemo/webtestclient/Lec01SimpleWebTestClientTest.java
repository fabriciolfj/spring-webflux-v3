package com.github.webfluxdemo.webtestclient;

import com.github.webfluxdemo.dto.ResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class Lec01SimpleWebTestClientTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void fluentAssertionTest() {
        this.webClient
                .get()
                .uri("/api/v2/math/{square}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponseDto.class)
                .value(v -> Assertions.assertThat(v.getOutput()).isEqualTo(25));
    }

    @Test
    public void stepVerifier() {
        var flux = this.webClient
                .get()
                .uri("/api/v2/math/{square}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(ResponseDto.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();
    }
}
