package com.api1.ms1.service;

import com.api1.ms1.model.MessageDto;
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

    public void logInteractionTime(MessageDto messageDto) {
        int messageCount = 0;
        long overallTime = 0;
        Date now = new Date();
        Date firstServiceTime = messageDto.getMs1TimeStamp();
        Date secondServiceTime = messageDto.getMs2TimeStamp();
        Date thirdServiceTime = messageDto.getMs3TimeStamp();
        if (firstServiceTime != null) {
            messageCount++;
            overallTime = Math.abs(now.getTime() - firstServiceTime.getTime());
        }
        if (secondServiceTime != null) {
            messageCount++;
        }
        if (thirdServiceTime != null) {
            messageCount++;
        }
        log.info("overall time = {}, count of nessages = {}", overallTime, messageCount);
    }

    public boolean sendStopMessage() {
        messagingTemplate.convertAndSend("/MS2/destroy", "stop");
        return true;
    }
}
