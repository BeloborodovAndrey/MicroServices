package com.api3.ms3.service;

import com.api3.ms3.model.MessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class RestTemplateService {

    @Value("MS1")
    String url;
    RestTemplate restTemplate;
    public void sendMessage(MessageDto messageDto) {
        try {
            restTemplate.postForObject(url, messageDto, MessageDto.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("can't execute post request");
            throw ex;
        }
    }
}
