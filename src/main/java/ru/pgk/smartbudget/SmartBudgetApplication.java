package ru.pgk.smartbudget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SmartBudgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBudgetApplication.class, args);
    }

}
