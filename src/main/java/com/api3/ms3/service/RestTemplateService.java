package com.api3.ms3.service;

import com.api3.ms3.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RestTemplateService {

    RestTemplate restTemplate = new RestTemplate();
    public void sendMessage(MessageDto messageDto) {
        String url="http://localhost:8081/MS1";
        try {
            restTemplate.postForObject(url, messageDto, MessageDto.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("can't execute post request");
            throw ex;
        }
    }
}
