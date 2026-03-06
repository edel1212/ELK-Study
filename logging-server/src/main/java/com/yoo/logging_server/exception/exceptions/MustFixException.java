package com.yoo.logging_server.exception.exceptions;

// Exception 을 상속함으로 CheckedException 처리
public class MustFixException extends Exception{
    public MustFixException(String message) {
        super(message);
    }
}
