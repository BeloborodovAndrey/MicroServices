package com.api1.ms1.service;

import com.api1.ms1.config.handlers.SessionHandler;
import com.api1.ms1.config.handlers.SessionHandlerForDestroy;
import com.api1.ms1.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Service for init and stop messaging
 */
@Component
@Slf4j
public class InitDestroyService {

    private DestroyService destroyService;
    private SessionHandler sessionHandler;
    private SessionHandlerForDestroy sessionHandlerForDestroy;
    private WebSocketStompClient stompClient;

    @Value("${server.url}")
    private String url;

    @Value("${server.url-destroy}")
    private String urlDestroy;

    public InitDestroyService(DestroyService destroyService, SessionHandler sessionHandler, SessionHandlerForDestroy sessionHandlerForDestroy, WebSocketStompClient stompClient) {
        this.destroyService = destroyService;
        this.sessionHandler = sessionHandler;
        this.sessionHandlerForDestroy = sessionHandlerForDestroy;
        this.stompClient = stompClient;
    }

    public boolean init() {
        log.info("Starting message exchanging");
        try {
            stompClient.connect(url, sessionHandler);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("some problems with sending first message" + ex.getMessage());
            return false;
        }
    }

    public boolean continueSending(MessageDto messageDto) {
        log.info("Starting message exchanging session {}", messageDto.getSessionId() + 1);
        try {
            messageDto.setSessionId(messageDto.getSessionId() + 1);
            sessionHandler.setMessageDto(messageDto);
            stompClient.connect(url, sessionHandler);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("some problems with sending first message" + ex.getMessage());
            return false;
        }
    }

    public void stop() {
        log.info("Stopping message exchanging");
        try {
            MessageDto messageDto = MessageDto.builder().sessionId(-1).build();
            sessionHandlerForDestroy.setMessageDto(messageDto);
            stompClient.connect(urlDestroy, sessionHandlerForDestroy);
            destroyService.destroy();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("some problems with sending stopping message" + ex.getMessage());
        }
    }

}
