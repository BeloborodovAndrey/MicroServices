package com.api1.ms1.service;

import com.api1.ms1.runner.Ms1Application;
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
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Ms1Application.class)
                .web(WebApplicationType.NONE).run();

        int exitCode = SpringApplication.exit(ctx, () -> 0);
        log.info("first service have stopped");
        System.exit(exitCode);
    }
}
