package com.yoo.logging_server.exception.exceptions;

// RuntimeException 을 상속함으로 UncheckedException 처리
public class NotFoundItemException extends RuntimeException{
    public NotFoundItemException(String message) {
        super(message);
    }
}
