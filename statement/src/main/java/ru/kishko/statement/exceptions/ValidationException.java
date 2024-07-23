package ru.kishko.statement.exceptions;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {
    private final BindingResult errors;

    public ValidationException(BindingResult errors) {
        this.errors = errors;
    }

    public BindingResult getErrors() {
        return errors;
    }
}
