package com.github.webfluxdemo.route;

import com.github.webfluxdemo.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("router/table/{input}", requestHandler::tableHandler)
                .GET("router/stream/{input}", requestHandler::tableStreamHandler)
                .POST("router/multiply", requestHandler::multiply)
                .build();
    }
}
