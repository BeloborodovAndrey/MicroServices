package com.api1.ms1.controller;

import com.api1.ms1.model.dto.MessageDto;
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

    @GetMapping("/MS1")
    public ResponseEntity<Boolean> getDroneBatteryLevel(
            @RequestBody MessageDto messageDto
    ) {
        return new ResponseEntity<>(messageService.saveMessage(messageDto), HttpStatus.OK);
    }
}
