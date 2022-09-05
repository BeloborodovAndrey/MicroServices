package com.api2.ms2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    public static final String START_ENDPOINT_MESSAGE_MAPPING = "/start";

    public static final String DESTROY_MESSAGE_MAPPING = "/destroy";

    public static final String WS_ENDPOINT_PREFIX = "/app";

    public static final String WS_TOPIC_DESTINATION_PREFIX = "/topic";

    public static final String WS_TOPIC = WS_TOPIC_DESTINATION_PREFIX+"/messages";

    public static final String WS_TOPIC_DESTROY = WS_TOPIC_DESTINATION_PREFIX+"/messagesDestroy";


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker(WS_TOPIC_DESTINATION_PREFIX);
        config.setApplicationDestinationPrefixes(WS_ENDPOINT_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(START_ENDPOINT_MESSAGE_MAPPING)
                .setAllowedOrigins("*")
                .withSockJS();

        registry.addEndpoint(DESTROY_MESSAGE_MAPPING)
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
