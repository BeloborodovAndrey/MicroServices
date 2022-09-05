package com.api2.ms2.service;

import com.api2.ms2.model.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;

/**
 * Service for sending message with kafka
 */
@Service
@Slf4j
@EnableKafka
public class ProducerKafkaService {

    private KafkaTemplate<String, String> kafkaTemplate;
    private NewTopic topic1;

    private final ObjectMapper objectMapper;

    public ProducerKafkaService(KafkaTemplate<String, String> kafkaTemplate, NewTopic topic1, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic1 = topic1;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(MessageDto messageDto) {
        messageDto.setMs2TimeStamp(new Date());
        String message = "";
        try {
            message = objectMapper.writeValueAsString(messageDto);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
            ListenableFuture<SendResult<String, String>> future
                    = this.kafkaTemplate.send(topic1.name(), message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Message created: "
                        + messageDto + " with offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Message created : " + messageDto, ex);
            }
        });
    }
    public boolean sendStopMessage(MessageDto messageDto) {
        String message = "";
        try {
            message = objectMapper.writeValueAsString(messageDto);
            kafkaTemplate.send("server.destroy", message);
            return true;
        } catch (Exception ex) {
            log.error("problem with kafka sending second message" + ex.getMessage());
            return false;
        }
    }
}
