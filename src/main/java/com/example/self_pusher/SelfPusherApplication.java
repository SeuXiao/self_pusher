package com.example.self_pusher;

import com.example.self_pusher.service.PusherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SelfPusherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfPusherApplication.class, args);
    }
}
