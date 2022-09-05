package com.api1.ms1.config.handlers;

import com.api1.ms1.model.MessageDto;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@Slf4j
public class SessionHandler extends StompSessionHandlerAdapter {

    private MessageDto messageDto = null;
    public static final String START_ENDPOINT_MESSAGE_MAPPING = "/start";

    public static final String WS_ENDPOINT_PREFIX = "/app";

    public static final String WS_TOPIC_DESTINATION_PREFIX = "/topic";

    public static final String WS_TOPIC = WS_TOPIC_DESTINATION_PREFIX+"/messages";

    @Synchronized
    public void setMessageDto(MessageDto messageDto) {
        this.messageDto = messageDto;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        try {
            if(messageDto == null) {
                messageDto = MessageDto.create();
            }
                this.subscribeAndSend(session, messageDto);
        } catch(Exception e) {
            log.error("Error while sending message" + e.getMessage());
        }

    }

    protected void subscribeAndSend(StompSession session, MessageDto messageDto) {
        session.subscribe(WS_TOPIC, this);
        session.send(WS_ENDPOINT_PREFIX+START_ENDPOINT_MESSAGE_MAPPING, messageDto);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("message has been received by second service {}", payload);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        super.handleException(session, command, headers, payload, exception);
    }

}
