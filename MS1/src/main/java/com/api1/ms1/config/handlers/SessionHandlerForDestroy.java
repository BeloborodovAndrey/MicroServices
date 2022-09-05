package com.api1.ms1.config.handlers;

import com.api1.ms1.model.MessageDto;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class SessionHandlerForDestroy extends SessionHandler {
    public static final String WS_TOPIC_DESTROY = WS_TOPIC_DESTINATION_PREFIX+"/stop";

    public static final String DESTROY_MESSAGE_MAPPING = "/destroy";
    protected void subscribeAndSend(StompSession session, MessageDto messageDto) {
        session.subscribe(WS_TOPIC_DESTROY, this);
        session.send(WS_ENDPOINT_PREFIX + DESTROY_MESSAGE_MAPPING, messageDto);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Void.class;
    }
}
