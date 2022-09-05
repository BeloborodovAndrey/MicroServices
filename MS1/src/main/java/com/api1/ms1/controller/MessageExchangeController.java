package com.api1.ms1.controller;

import com.api1.ms1.model.MessageDto;
import com.api1.ms1.service.InitDestroyService;
import com.api1.ms1.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class MessageExchangeController {

    private MessageService messageService;

    private InitDestroyService initDestroyService;

    @PostMapping("/MS1")
    public ResponseEntity<Boolean> processMessage(
            @RequestBody MessageDto messageDto
    ) {
        messageDto.setEndTimeStamp(new Date());
        if (messageService.saveMessage(messageDto)) {
            messageService.logInteractionTime(messageDto);
            initDestroyService.continueSending(messageDto);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
