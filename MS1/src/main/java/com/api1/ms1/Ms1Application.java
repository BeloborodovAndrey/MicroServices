package com.api1.ms1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Ms1Application {

    public static void main(String[] args) {
        SpringApplication.run(Ms1Application.class, args);
    }

}
