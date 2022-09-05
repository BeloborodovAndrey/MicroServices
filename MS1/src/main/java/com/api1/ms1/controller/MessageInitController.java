package com.api1.ms1.controller;

import com.api1.ms1.service.InitDestroyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class MessageInitController {

    private InitDestroyService initDestroyService;

    @GetMapping("/start")
    public ResponseEntity<Boolean> startMessaging() {
        return new ResponseEntity<>(initDestroyService.init(), HttpStatus.OK);
    }

    @GetMapping("/stop")
    public ResponseEntity<Boolean> stopMessaging() {
        initDestroyService.stop();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
