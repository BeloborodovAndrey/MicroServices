package com.api3.ms3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Ms3Application {
    public static void main(String[] args) {
        SpringApplication.run(Ms3Application.class, args);
    }

}
