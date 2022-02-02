package com.github.webfluxdemo.controller;

import com.github.webfluxdemo.dto.MathRequestDto;
import com.github.webfluxdemo.dto.ResponseDto;
import com.github.webfluxdemo.exception.InputValidationException;
import com.github.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/math")
public class ReactiveMathController {

    private final ReactiveMathService reactiveMathService;

    @GetMapping("/{square}")
    public Mono<ResponseDto> find(@PathVariable("square") final int square) {
        return reactiveMathService.findSquare(square);
    }

    @GetMapping(value = "/{square}/table/validate")
    public Mono<ResponseDto> findTableValidate(@PathVariable("square") final int square) {
        if (square < 10 || square > 20)
            throw new InputValidationException(square);

        return reactiveMathService.findSquare(square);
    }

    @GetMapping(value = "/{square}/table/monoerror")
    public Mono<ResponseDto> findTableValidateMonoError(@PathVariable("square") final int square) {
        return Mono.just(square)
                .handle((integer, sink) -> {
                    if (integer < 10 || integer > 20) {
                        sink.error(new InputValidationException(integer));
                    } else {
                        sink.next(integer);
                    }
                }).cast(Integer.class)
                .flatMap(i -> reactiveMathService.findSquare(i));
    }

    @GetMapping(value = "/{square}/table/assignment")
    public Mono<ResponseEntity<ResponseDto>> findTableValidateAssignment(@PathVariable("square") final int square) {
        return Mono.just(square)
                .filter(i -> i >= 10 && i <= 20)
                .flatMap(i -> reactiveMathService.findSquare(i))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/{square}/table", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseDto> findTable(@PathVariable("square") final int square) {
        return reactiveMathService.findTableSquare(square);
    }

    @PostMapping
    public Mono<ResponseDto> calculate(@RequestBody Mono<MathRequestDto> dto, @RequestHeader final Map<String, String> headers) {
        System.out.println("Headers: " + headers);
        return reactiveMathService.tableCalculate(dto);
    }
}
