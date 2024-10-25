package com.example.aop.task2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class SomeServiceImpl implements SomeService {
    @Override
    public String firstMethod(int intParam, String stringParam) {
        return String.format("Result after some operations with values %d and %s", intParam, stringParam);
    }

    @Override
    public int secondMethod(LocalDate dateParam) {
        return dateParam.getDayOfMonth();
    }
}
