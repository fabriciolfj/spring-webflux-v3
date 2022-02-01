package com.github.webfluxdemo.service;

import com.github.webfluxdemo.dto.ResponseDto;
import com.github.webfluxdemo.util.SleepUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class MathService {

    public ResponseDto findSquare(final int input) {
        return new ResponseDto(input * input);
    }

    public List<ResponseDto> multiplicationTable(final int input) {
        return IntStream.rangeClosed(1, 10)
                .peek( i -> SleepUtil.sleepSeconds(i))
                .peek(i -> System.out.println("math-service processing: " + i))
                .mapToObj(i -> new ResponseDto(i * input))
                .toList();
    }
}
