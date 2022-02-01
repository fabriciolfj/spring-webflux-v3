package com.github.webfluxdemo.service;

import com.github.webfluxdemo.dto.MathRequestDto;
import com.github.webfluxdemo.dto.ResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<ResponseDto> findSquare(final int square) {
        return Mono.fromSupplier(() -> new ResponseDto(square * square));
    }

    public Flux<ResponseDto> findTableSquare(final int square) {
       return Flux.range(1, 10)
               .delayElements(Duration.ofSeconds(1))
               .doOnNext(i -> System.out.println("Event: " + i))
               .map(i -> new ResponseDto(i * square));
    }

    public Mono<ResponseDto> tableCalculate(final Mono<MathRequestDto> dto) {
        return dto.map(i -> i.getSquareOne() * i.getSquareTwo())
                .map(ResponseDto::new);
    }
}
