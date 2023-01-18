package com.example.self_pusher.controller;

import com.example.self_pusher.entity.Day;
import com.example.self_pusher.entity.Sentence;
import com.example.self_pusher.entity.Weather;
import com.example.self_pusher.service.PusherService;
import com.example.self_pusher.utils.PushImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PusherController {
    @Autowired
    private PusherService pusherService;

    @PostMapping("/getToken")
    public String getToken(){
        return pusherService.getToken();
    }
    @PostMapping("/getAllPeople")
    public List<String> getAllPeople(){
        return pusherService.getAllPeople();
    }
    @PostMapping("/getWeather")
    public List<Weather> getWeather(){
        return pusherService.getWeather();
    }
    @PostMapping("/getSentence")
    public Sentence getSentence(){
        return pusherService.getSentence();
    }

    @PostMapping("/getDay")
    public Day getDay(){
        return pusherService.getDay();
    }
    @PostMapping("/testMessage")
    public void testMessage(){
        PushImpl.testMessage();
    }
}
