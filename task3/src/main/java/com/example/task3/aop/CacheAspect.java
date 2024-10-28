package com.example.task3.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Aspect
@Component
@Slf4j
public class CacheAspect {
    private final Map<String, List<CacheDTO>> cachedValues = new HashMap<>();

    @Pointcut("@annotation(Cacheable)")
    public void cacheablePointcut() {
    }

    @Around("cacheablePointcut()")
    public Object processCacheableAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String methodKey = joinPoint.getSignature().toLongString();
            Object methodResult = findResultOfMethodByArgs(methodKey, joinPoint.getArgs());
            if (methodResult == null) {
                methodResult = joinPoint.proceed(joinPoint.getArgs());
                saveMethodResultIntoCache(methodKey, joinPoint.getArgs(), methodResult);
            }

            log.info(
                    String.format(
                            "Around advice on method with name %s. Completed successfully, parameters {%s}, method result %s",
                            joinPoint.getSignature().toShortString(),
                            getMethodArgs(joinPoint),
                            methodResult.toString()
                    )
            );
            return methodResult;
        } catch (Throwable e) {
            log.info(
                    String.format(
                            "Around advice on method with name %s,  parameters {%s}. Completed with exception %s",
                            joinPoint.getSignature().toShortString(),
                            getMethodArgs(joinPoint),
                            e
                    )
            );
            throw e;
        }
    }

    private Object findResultOfMethodByArgs(String methodKey, final Object[] args) {
        List<CacheDTO> methodCachedValues = cachedValues.get(methodKey);
        if (methodCachedValues != null && !methodCachedValues.isEmpty()) {
            return methodCachedValues.stream()
                    .filter(item -> Arrays.equals(args, item.getArgs()))
                    .findFirst()
                    .map(item -> {
                        log.info("Method result loaded from cache {}", item);
                        return item.getResult();
                    })
                    .orElse(null);
        }
        return null;
    }

    private void saveMethodResultIntoCache(String methodKey, Object[] args, Object methodResult) {
        List<CacheDTO> methodCachedValues =
                cachedValues.computeIfAbsent(
                        methodKey,
                        key -> new ArrayList<>()
                );
        CacheDTO cache = new CacheDTO(args, methodResult);
        methodCachedValues.add(cache);

        log.info("Method result saved into cache {}", cache);
    }

    private String getMethodArgs(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Parameter[] methodParams = method.getParameters();
        Object[] args = joinPoint.getArgs();
        int paramCount = methodParams.length;

        List<String> paramsWithValues = new ArrayList<>();
        for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
            paramsWithValues.add(
                    String.format(
                            "(%d. param name: %s, value: %s)",
                            paramIndex + 1,
                            methodParams[paramIndex].getName(),
                            args[paramIndex].toString()
                    )
            );
        }

        return String.join("; ", paramsWithValues);
    }
}
