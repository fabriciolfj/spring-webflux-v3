package com.github.webfluxdemo.webtestclient;

import com.github.webfluxdemo.controller.ParamsController;
import com.github.webfluxdemo.controller.ReactiveMathController;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Map;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class Lec02ControllerGetTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ReactiveMathService reactiveMathService;

    Map<String, Integer> params = Map.of(
            "count", 10,
            "page" ,  20
    );

    @Test
    public void queryParamsTest() {
        this.webClient
                .get()
                .uri(b ->  b.path("/jobs").query("count={count}&page={page}").build(params))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(2).contains(10, 20);
    }

    @Test
    public void streamResponseTest() {

        Flux<ResponseDto> flux = Flux.range(1, 3)
                .map(ResponseDto::new)
                .delayElements(Duration.ofMillis(100));

        Mockito.when(reactiveMathService.findTableSquare(Mockito.anyInt())).thenReturn(flux);

        this.webClient
                .get()
                .uri("/api/v2/math//{square}/table", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(ResponseDto.class)
                .hasSize(3);
    }

    @Test
    public void listResponseTest() {

        Flux<ResponseDto> flux = Flux.range(1, 3)
                        .map(ResponseDto::new);

        Mockito.when(reactiveMathService.findTableSquare(Mockito.anyInt())).thenReturn(flux);

        this.webClient
                .get()
                .uri("/api/v2/math//{square}/table", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(ResponseDto.class)
                .hasSize(3);
    }

    @Test
    public void fluentAssertionTest() {
        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new ResponseDto(25)));

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
    public void fluentAssertionErrorTest() {
        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new ResponseDto(25)));

        this.webClient
                .get()
                .uri("/api/v2/math/{square}/table/monoerror", 5)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.input").isEqualTo(5);
    }
}
