package com.yoo.logging_server.controller;

import com.yoo.logging_server.exception.exceptions.MustFixException;
import com.yoo.logging_server.exception.exceptions.NotFoundItemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/home")
@RestController
@Slf4j
public class MainController {
    @GetMapping("/{itemId}")
    public ResponseEntity<String> makeNotFoundItemError(@PathVariable String itemId){
        // 에러 생성
        throw new NotFoundItemException(itemId);
    }

    @GetMapping
    public ResponseEntity<String> makeCheckedException() throws Exception{
        // 에러 생성
        throw new MustFixException("어떻게든 처리를 해줘야 함 try/catch || throw Exception");
    }

}
