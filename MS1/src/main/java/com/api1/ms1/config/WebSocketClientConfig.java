package com.api1.ms1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSocketClientConfig {


    @Bean
    public WebSocketClient webSocketClient()
    {
        List<Transport> transports = new ArrayList<Transport>(1);
        transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
        return new SockJsClient(transports);
    }

    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient) {
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        return  stompClient;
    }
}
