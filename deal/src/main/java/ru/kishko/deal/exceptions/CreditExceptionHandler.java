package ru.kishko.deal.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kishko.api.exceptions.InvalidAgeException;
import ru.kishko.api.exceptions.InvalidPassportIssueDate;

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

    @ExceptionHandler(value = {InvalidAgeException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(InvalidAgeException invalidAgeException) {

        log.error("Exception occurred: ", invalidAgeException); // Логирование ошибки

        ru.kishko.api.exceptions.Exception exception = new ru.kishko.api.exceptions.Exception(
                invalidAgeException.getMessage(),
                invalidAgeException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(exception, exception.getHttpStatus());

    }

    @ExceptionHandler(value = {InvalidPassportIssueDate.class})
    public ResponseEntity<Object> handleProjectNotFoundException(InvalidPassportIssueDate invalidPassportIssueDate) {

        log.error("Exception occurred: ", invalidPassportIssueDate); // Логирование ошибки

        ru.kishko.api.exceptions.Exception exception = new ru.kishko.api.exceptions.Exception(
                invalidPassportIssueDate.getMessage(),
                invalidPassportIssueDate.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(exception, exception.getHttpStatus());

    }

}
