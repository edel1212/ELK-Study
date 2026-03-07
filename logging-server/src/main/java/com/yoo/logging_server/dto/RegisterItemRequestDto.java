package com.yoo.logging_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterItemRequestDto {
    private String itemName;
    private long amount;
}
