package com.github.webfluxdemo.controller;

import com.github.webfluxdemo.dto.MathRequestDto;
import com.github.webfluxdemo.dto.ResponseDto;
import com.github.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
