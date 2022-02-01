package com.github.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ResponseDto {

    private Date date = new Date();
    private int output;

    public ResponseDto(int output) {
        this.output = output;
    }
}
