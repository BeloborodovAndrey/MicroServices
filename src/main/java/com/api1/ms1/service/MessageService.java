package com.api1.ms1.service;

import com.api1.ms1.model.dto.MessageDto;
import com.api1.ms1.repository.MessageRepository;
import com.api1.ms1.repository.entity.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for sending message with socket
 */
@Service
@Slf4j
@AllArgsConstructor
public class MessageService {

    private SimpMessagingTemplate messagingTemplate;
    private MessageRepository messageRepository;

    public boolean sendMessage() {
        messagingTemplate.convertAndSend("/MS2", createMessage());
        return true;
    }

    public boolean saveMessage(MessageDto messageDto) {
        try {
            messageRepository.save(MessageEntity.from(messageDto));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Can't save msg: " + ex.getMessage());
            return false;
        }
    }

    private MessageDto createMessage() {
        return MessageDto.builder()
                .sessionId(1)
                .endTimeStamp(new Date())
                .build();
    }
}
