package ru.kishko.dossier.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CreditExceptionHandler {

    @ExceptionHandler(value = {MailException.class})
    public ResponseEntity<Object> handleMailException(MailException mailException) {
        log.error("Mail exception occurred: ", mailException);
        Exception exception = new Exception(
                mailException.getMessage(),
                mailException.getCause(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }
}
