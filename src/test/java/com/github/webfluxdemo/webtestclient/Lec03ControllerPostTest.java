package com.github.webfluxdemo.webtestclient;

import com.github.webfluxdemo.controller.ReactiveMathController;
import com.github.webfluxdemo.dto.MathRequestDto;
import com.github.webfluxdemo.dto.ResponseDto;
import com.github.webfluxdemo.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ReactiveMathController.class)
public class Lec03ControllerPostTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void postTest() {
        Mockito.when(reactiveMathService.tableCalculate(Mockito.any())).thenReturn(Mono.just(new ResponseDto(20)));

        webTestClient.post()
                .uri("/api/v2/math")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth("username", "password"))
                .headers(h -> h.set("someKey", "someValue"))
                .bodyValue(new MathRequestDto())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ResponseDto.class)
                .value(v -> Assertions.assertThat(v.getOutput()).isEqualTo(20));
    }
}
