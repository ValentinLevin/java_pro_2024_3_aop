package com.example.task4.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
    private final String contextPath;

    public LoggerAspect(@Value("${server.servlet.context-path}") String contextPath) {
        this.contextPath = contextPath;
    }

    @Pointcut("@annotation(loggable)")
    public void loggableMethod(Loggable loggable) {
    }

    @Before(value = "loggableMethod(loggable)", argNames = "joinPoint, loggable")
    @Async
    public void beforeLoggableMethod(JoinPoint joinPoint, Loggable loggable) {
        Method method = getInvokedMethod(joinPoint);

        List<String> methodParametersWithValues =
                getMethodParamNamesAndValues(
                        method.getParameters(),
                        joinPoint.getArgs(),
                        loggable.ignoredParameters()
                );

        log.info("-------------------------- {}", LocalDateTime.now());
        log.info("Method invoked: {} ({})", method.getName(), method.getDeclaringClass().getName());
        log.info("Endpoint path: {}", getEndpointUrl(method));
        log.info("Args:");
        methodParametersWithValues.forEach(item -> log.info(" - {}", item));
        log.info("--------------------------");
    }

    private Method getInvokedMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }

    private List<String> getMethodParamNamesAndValues(
            Parameter[] methodParameters,
            Object[] methodArgs,
            String[] noLoggingParameters
    ) {
        List<String> noLoggingParametersList = List.of(noLoggingParameters);
        int paramCount = methodParameters.length;

        List<String> paramsWithValues = new ArrayList<>();
        for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
            if (!noLoggingParametersList.contains(methodParameters[paramIndex].getName())) {
                paramsWithValues.add(
                        String.format(
                                "param name: %s, value: %s",
                                methodParameters[paramIndex].getName(),
                                methodArgs[paramIndex].toString()
                        )
                );
            }
        }

        return paramsWithValues;
    }

    public String getEndpointUrl(Method method) {
        String classPath = getClassPartOfEndpointUrl(method.getDeclaringClass());
        String methodPath = getMethodPartOfEndpointUrl(method);
        return classPath.concat(methodPath);
    }

    private String getClassPartOfEndpointUrl(Class<?> controllerClass) {
        RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
        if (classMapping != null) {
            return classMapping.value().length > 0 ? classMapping.value()[0] : "";
        }
        return "";
    }

    private String getMethodPartOfEndpointUrl(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            RequestMapping requestMapping = annotation.annotationType().getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                try {
                    Method methodValue = annotation.annotationType().getMethod("value");
                    String[] values = (String[]) methodValue.invoke(annotation);
                    return String.format(
                            "method %s, path %s%s",
                            requestMapping.method()[0].name(),
                            this.contextPath,
                            String.join(", ", values)
                    );
                } catch (Exception e) {
                    return "";
                }
            }
        }
        return "";
    }
}
