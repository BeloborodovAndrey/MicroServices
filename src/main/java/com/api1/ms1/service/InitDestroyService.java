package com.api1.ms1.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Service for init and stop messaging
 */
@Component
@Slf4j
@AllArgsConstructor
public class InitDestroyService {

    private MessageService messageService;
    private DestroyService destroyService;

    public boolean init() {
        log.info("Starting message exchanging");
        try {
            return messageService.sendMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("some problems with sending first message" + ex.getMessage());
            return false;
        }
    }

    public void stop() {
        log.info("Stopping message exchanging");
        try {
            if (messageService.sendStopMessage()) {
                destroyService.destroy();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("some problems with sending stopping message" + ex.getMessage());
        }
    }

}
