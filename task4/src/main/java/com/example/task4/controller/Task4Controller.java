package com.example.task4.controller;

import com.example.task4.aop.Loggable;
import com.example.task4.dto.SomeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class Task4Controller {
    @PostMapping("/first-method")
    @Loggable(ignoredParameters = "stringValue")
    public String firstEndpoint(
            @RequestParam(name = "int-param", defaultValue = "-1") int intValue,
            @RequestParam(name = "string-param", defaultValue = "none") String stringValue,
            @RequestBody SomeDTO objectValue
    ) {
        return String.format("Endpoint 'firstEndpoint', %s", LocalDateTime.now());
    }

    @GetMapping("/second-method")
    @Loggable()
    public int secondEndpoint(@RequestParam("int-param") int intValue) {
        return -intValue;
    }
}
