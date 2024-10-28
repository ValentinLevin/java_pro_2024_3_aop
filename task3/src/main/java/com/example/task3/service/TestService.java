package com.example.task3.service;

import com.example.task3.dto.TestDTO;

import java.time.LocalDateTime;

public interface TestService {
    String method1(String stringValue, int intValue, TestDTO objectValue);
    String method1Duplicate(String stringValue, int intValue, TestDTO objectValue);

    String method2(TestDTO objectValue);

    LocalDateTime method3(int intValue);
}
