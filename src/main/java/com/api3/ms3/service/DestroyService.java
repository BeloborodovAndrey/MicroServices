package com.api3.ms3.service;

import com.api3.ms3.Ms3Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DestroyService {

    public void destroy() {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Ms3Application.class)
                .web(WebApplicationType.NONE).run();

        int exitCode = SpringApplication.exit(ctx, () -> 0);
        log.info("third service have stopped");
        System.exit(exitCode);
    }
}
