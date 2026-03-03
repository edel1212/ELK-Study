package com.yoo.logging_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/home")
@RestController
@Slf4j
public class MainController {

    @GetMapping
    public ResponseEntity<String> getHi(){
        log.info("Hi~~");
        return ResponseEntity.ok("Hello World");
    }
}
