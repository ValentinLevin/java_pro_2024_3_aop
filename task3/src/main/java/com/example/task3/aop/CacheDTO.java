package com.example.task3.aop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
public class CacheDTO {
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final Object[] args;
    private final Object result;
}
