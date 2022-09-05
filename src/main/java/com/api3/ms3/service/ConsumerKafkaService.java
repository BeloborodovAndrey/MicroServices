package com.api3.ms3.service;

import com.api3.ms3.model.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for receiving kafka message
 */
@Slf4j
@Service
@EnableKafka
public class ConsumerKafkaService {

    private final ObjectMapper objectMapper;
    private final RestTemplateService restTemplateService;

    private final DestroyService destroyService;

    public ConsumerKafkaService(ObjectMapper objectMapper, RestTemplateService restTemplateService, DestroyService destroyService) {
        this.objectMapper = objectMapper;
        this.restTemplateService = restTemplateService;
        this.destroyService = destroyService;
    }


    @KafkaListener(topics = "message",
            groupId = "test-consumer-group",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void consume(String messageDto) {
        log.info(String.format("Message created -> %s", messageDto));
        if (messageDto != null) {
            try {
                MessageDto dto = objectMapper.readValue(messageDto, MessageDto.class);
                dto.setMs3TimeStamp(new Date());
                restTemplateService.sendMessage(dto);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("error with destroying third service: " + ex.getMessage());
            }
        }
    }

    @KafkaListener(topics = "server.destroy",
            groupId = "test-consumer-group",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void consumeStop(String message) {
        if (message.isEmpty()) {
            log.warn("message is empty");
            return;
        }
            try {
                MessageDto messageDto = objectMapper.readValue(message, MessageDto.class);
                log.info("=> consumed {}", messageDto);
                if (messageDto.getSessionId() == -1) {
                    destroyService.destroy();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("error with third message sending: " + ex.getMessage());
            }
    }
}
