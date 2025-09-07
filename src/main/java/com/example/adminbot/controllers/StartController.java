package com.example.adminbot.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/start")
@RequiredArgsConstructor
public class StartController {


    @GetMapping("/startMessage")
    public ResponseEntity<Map<String, String>> getStartMessage(){
        return ResponseEntity.status(200)
                .body(Map.of("message", "Hello World!"));
    }

}
