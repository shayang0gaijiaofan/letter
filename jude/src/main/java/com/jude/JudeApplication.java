package com.jude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JudeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudeApplication.class, args);
    }
}
