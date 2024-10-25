package com.example.aop.task2.controller;

import com.example.aop.task2.aop.Loggable;
import com.example.aop.task2.service.SomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/aop")
public class SomeController {
    private final SomeService someService;

    public SomeController(SomeService someService) {
        this.someService = someService;
    }

    @GetMapping("/first-method")
    @Loggable(excludeParameters = "stringValue")
    public String firstEndpoint(
            @RequestParam(name = "int-param", defaultValue = "-1") int intValue,
            @RequestParam(name = "string-param", defaultValue = "none") String stringValue
    ) {
        return this.someService.firstMethod(intValue,stringValue);
    }

    @GetMapping("/second-method")
    @Loggable()
    public int secondEndpoint() {
        return this.someService.secondMethod(LocalDate.now());
    }
}
