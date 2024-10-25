package com.example.aop.task2.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
    @Pointcut("@annotation(Loggable)")
    public void loggableMethod() {
    }

    @Before("loggableMethod()")
    public void beforeLoggableMethod(JoinPoint joinPoint) {
        log.info("before");
    }
}
