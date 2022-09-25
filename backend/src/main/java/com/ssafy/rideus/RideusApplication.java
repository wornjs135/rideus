package com.ssafy.rideus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RideusApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideusApplication.class, args);
    }

}
