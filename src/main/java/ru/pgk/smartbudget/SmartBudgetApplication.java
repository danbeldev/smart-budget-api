package ru.pgk.smartbudget;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SmartBudgetApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartBudgetApplication.class, args);
    }

}
