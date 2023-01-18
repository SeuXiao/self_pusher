package com.example.self_pusher.entity;

import lombok.Data;

@Data
public class Weather {
    private String temp;
    private String feelsLike;
    private String text;
}