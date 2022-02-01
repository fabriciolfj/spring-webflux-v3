package com.github.webfluxdemo.controller;

import com.github.webfluxdemo.dto.ResponseDto;
import com.github.webfluxdemo.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/math")
public class MathController {

    private final MathService mathService;

    @GetMapping("/{square}")
    public ResponseDto findSquare(@PathVariable("square") final int square) {
        return mathService.findSquare(square);
    }

    @GetMapping("/{square}/table")
    public List<ResponseDto> findTable(@PathVariable("square") final int square) {
        return mathService.multiplicationTable(square);
    }
}
