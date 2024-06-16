package ru.kishko.deal.exceptions;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            ValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getErrors().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException feignException) {
        log.error("Credit exception occurred: ", feignException);
        Exception exception = new Exception(
                feignException.getMessage(),
                feignException.getCause(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }
}
