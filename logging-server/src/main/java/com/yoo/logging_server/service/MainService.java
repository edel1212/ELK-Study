package com.yoo.logging_server.service;

import com.yoo.logging_server.dto.RegisterItemRequestDto;

public interface MainService {
    String registerItem(RegisterItemRequestDto registerItemRequestDto);
    String modifyItem(String itemId,RegisterItemRequestDto registerItemRequestDto);
}
