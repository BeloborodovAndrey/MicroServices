package com.api2.ms2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
@EnableKafka
public class Ms2Application {

    public static void main(String[] args) {
        SpringApplication.run(Ms2Application.class, args);
    }

}
