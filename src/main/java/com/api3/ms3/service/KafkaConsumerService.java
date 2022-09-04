package com.api3.ms3.service;

import com.api3.ms3.model.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for receiving kafka message
 */
@Slf4j
@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;
    private final RestTemplateService restTemplateService;

    private DestroyService destroyService;

    public KafkaConsumerService(ObjectMapper objectMapper, RestTemplateService restTemplateService, DestroyService destroyService) {
        this.objectMapper = objectMapper;
        this.restTemplateService = restTemplateService;
        this.destroyService = destroyService;
    }

    @KafkaListener(id = "Message", topics = {"server.message"}, containerFactory = "singleFactory")
    public void consume(MessageDto messageDto) {
        if (messageDto != null) {
            try {
                messageDto.setMs3TimeStamp(new Date());
                destroyService.destroy();
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("error with destroying third service: " + ex.getMessage());
            }
        } else {
            log.warn("message is empty");
        }
    }

    @KafkaListener(id = "Message", topics = {"server.destroy"}, containerFactory = "singleFactory")
    public void consumeStop(MessageDto messageDto) {
        if (messageDto != null && messageDto.getSessionId() == -1) {
            try {
                log.info("=> consumed {}", writeValueAsString(messageDto));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("error with third message sending: " + ex.getMessage());
            }
        } else {
            log.warn("message is empty");
        }
    }

    private String writeValueAsString(MessageDto messageDto) {
        try {
            return objectMapper.writeValueAsString(messageDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + messageDto.toString());
        }
    }
}
