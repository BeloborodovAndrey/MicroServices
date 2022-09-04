package com.api1.ms1.controller;

import com.api1.ms1.model.MessageDto;
import com.api1.ms1.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class MessageExchangeController {

    private MessageService messageService;

    @PostMapping("/MS1")
    public ResponseEntity<Boolean> processMessage(
            @RequestBody MessageDto messageDto
    ) {
        if (messageService.saveMessage(messageDto)) {
            messageService.logInteractionTime(messageDto);
            messageService.sendMessage();
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
