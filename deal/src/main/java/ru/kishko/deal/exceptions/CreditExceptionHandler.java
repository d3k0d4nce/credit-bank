package ru.kishko.deal.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CreditExceptionHandler {

    @ExceptionHandler(value = {StatementNotFoundException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(StatementNotFoundException creditException) {
        log.error("Credit exception occurred: ", creditException);
        Exception exception = new Exception(
                creditException.getMessage(),
                creditException.getCause(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }

}
