package com.github.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9090")
                //.defaultHeaders(h -> h.setBasicAuth("username", "password"))
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction exchangeFunction) {
        final ClientRequest client =
                request.attribute("auth")
                        .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                        .orElse(request);

        return exchangeFunction.exchange(client);
    }

    /*private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        System.out.println("generating session token");
        ClientRequest clientRequest = ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("somo-lengthy-jwt")).build();

        return ex.exchange(clientRequest);
    }*/


    private ClientRequest withBasicAuth(final ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withOAuth(final ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("jwt"))
                .build();
    }

}
