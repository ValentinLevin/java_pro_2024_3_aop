package com.example.task3.service;

import com.example.task3.aop.Cacheable;
import com.example.task3.dto.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Override
    @Cacheable
    public String method1(String stringValue, int intValue, TestDTO objectValue) {
        log.info("method1 invoked with args stringValue{}, intValue {}, objectValue {}", stringValue, intValue, objectValue);
        return String.format("method1 result %s", LocalDateTime.now());
    }

    @Override
    @Cacheable
    public String method1Duplicate(String stringValue, int intValue, TestDTO objectValue) {
        log.info("method1Duplicate invoked with args stringValue{}, intValue {}, objectValue {}", stringValue, intValue, objectValue);
        return String.format("method1 result %s", LocalDateTime.now());
    }

    @Override
    @Cacheable
    public String method2(TestDTO objectValue) {
        log.info("method2 invoked with arg: objectValue {}", objectValue);
        return String.format("method2 result %s", LocalDateTime.now());
    }

    @Override
    @Cacheable
    public LocalDateTime method3(int intValue) {
        log.info("method3 invoked with arg: intValue {}", intValue);
        return LocalDateTime.now();
    }
}
