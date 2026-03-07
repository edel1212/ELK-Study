package com.yoo.logging_server.service;

import com.yoo.logging_server.dto.RegisterItemRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainServiceImpl implements MainService{
    @Override
    public String registerItem(RegisterItemRequestDto registerItemRequestDto) {

        log.debug("Entity 변경 내역 ~~~ 작성");
        return "OK";
    }
}
