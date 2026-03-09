package com.yoo.logging_server.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterItemRequestDto {
    private String itemName;
    private long amount;
}
