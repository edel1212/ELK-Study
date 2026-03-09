package com.yoo.logging_server.controller;

import com.yoo.logging_server.dto.RegisterItemRequestDto;
import com.yoo.logging_server.exception.exceptions.MustFixException;
import com.yoo.logging_server.exception.exceptions.NotFoundItemException;
import com.yoo.logging_server.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/home")
@RequiredArgsConstructor
@RestController
@Slf4j
public class MainController {

    private final MainService mainService;
    @GetMapping("/{itemId}")
    public ResponseEntity<String> makeNotFoundItemError(@PathVariable String itemId){
        // 에러 생성
        throw new NotFoundItemException(itemId);
    }

    @GetMapping("/info")
    public ResponseEntity<String> getQueryParam(String itemId){
        return ResponseEntity.ok("return to QueryParam");
    }

    @GetMapping
    public ResponseEntity<String> makeCheckedException() throws Exception{
        // 에러 생성
        throw new MustFixException("어떻게든 처리를 해줘야 함 try/catch || throw Exception");
    }

    @PostMapping
    public ResponseEntity<String> registerItem(@RequestBody RegisterItemRequestDto registerItemRequestDto){
        return ResponseEntity.ok(mainService.registerItem(registerItemRequestDto));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<String> modifyItem(@PathVariable String itemId, @RequestBody RegisterItemRequestDto registerItemRequestDto){
        return ResponseEntity.ok(mainService.modifyItem(itemId, registerItemRequestDto));
    }
}
