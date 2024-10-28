package com.example.task4;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Task4Application {
    public static void main(String[] args) {
        SpringApplication.run(Task4Application.class, args);
    }
}
