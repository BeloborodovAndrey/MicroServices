package com.api1.ms1.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for init and stop messaging
 */
@Service
@Slf4j
@AllArgsConstructor
public class MessageInitService {

    private MessageService messageService;
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

    public boolean stop() {
        return true;
    }

}
