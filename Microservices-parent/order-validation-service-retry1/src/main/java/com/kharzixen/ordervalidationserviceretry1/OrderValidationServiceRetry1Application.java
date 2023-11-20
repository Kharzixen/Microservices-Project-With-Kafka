package com.kharzixen.ordervalidationserviceretry1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderValidationServiceRetry1Application {
    public static void main(String[] args) {
        SpringApplication.run(OrderValidationServiceRetry1Application.class, args);
    }
}
