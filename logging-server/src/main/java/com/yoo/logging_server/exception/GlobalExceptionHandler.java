package com.yoo.logging_server.exception;

import com.yoo.logging_server.exception.exceptions.NotFoundItemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * UncheckedException 처리
     *
     * @param ex the exception
     * @return the response Entity
     */
    @ExceptionHandler(NotFoundItemException.class)
    public ResponseEntity<String> handleNotFoundItemException(NotFoundItemException ex){
        log.warn("{}를 찾지 못했습니다.", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이템을 찾지 못했습니다.");
    }
}
