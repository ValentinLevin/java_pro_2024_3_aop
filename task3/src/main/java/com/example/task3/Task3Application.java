package com.example.task3;

import com.example.task3.dto.TestDTO;
import com.example.task3.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class Task3Application {
    public static void main(String[] args) {
        SpringApplication.run(Task3Application.class, args);
    }

    @Bean
    public CommandLineRunner run(TestService testService) {
        return (String[] args) -> {
            /*
            * Приводится пример реализации кеширования результата метода
            * кеширование результата для каждого метода производится от сигнатуры метода,
            * что позволяет хранить данные для каждого метода отдельно.
            * Перед вызовом целевого метода производится проверка наличия ранее сохраненного результата
            * вызова с такими же параметрами.
            * Если таковой имеется, то результат возвращается из кеша не затрагивая целевой метод
            * */

            TestDTO testDTO1 = new TestDTO(1, "test string 1", LocalDateTime.now());
            TestDTO testDTO2 = new TestDTO(2, "test string 2", LocalDateTime.now());

            log.info("---------------------------------------");
            testService.method1("string value #1", 1, testDTO1);
            log.info("---------------------------------------");
            testService.method1("string value #2", 2, testDTO2);
            log.info("---------------------------------------");
            testService.method1("string value #1", 1, testDTO1);
            log.info("---------------------------------------");
            testService.method1("string value #1", 2, testDTO1);
            log.info("---------------------------------------");
            testService.method1Duplicate("string value #1", 1, testDTO1);
            log.info("---------------------------------------");
            testService.method2(testDTO1);
            log.info("---------------------------------------");
            testService.method2(testDTO2);
            log.info("---------------------------------------");
            testService.method3(1);
            log.info("---------------------------------------");
            testService.method3(1);
            log.info("---------------------------------------");
        };
    }
}
