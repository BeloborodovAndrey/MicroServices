package com.api2.ms2.controller;

import com.api2.ms2.model.MessageDto;
import com.api2.ms2.service.DestroyService;
import com.api2.ms2.service.ProducerKafkaService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.api2.ms2.config.WebsocketConfiguration.*;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class MessageExchangeController {

    private ProducerKafkaService producerKafkaService;
    private DestroyService destroyService;

    @MessageMapping(START_ENDPOINT_MESSAGE_MAPPING)
    @SendTo(WS_TOPIC)
    public void processMessage(MessageDto messageDto) {
            producerKafkaService.sendMessage(messageDto);
    }

    @MessageMapping(DESTROY_MESSAGE_MAPPING)
    @SendTo(WS_TOPIC_DESTROY)
    public void processDestroyRequest(MessageDto message) {
        if (message.getSessionId() == -1 && producerKafkaService.sendStopMessage(message)) {
            destroyService.destroy();
        }
    }
}
