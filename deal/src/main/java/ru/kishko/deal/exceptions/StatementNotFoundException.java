package ru.kishko.deal.exceptions;

public class StatementNotFoundException extends RuntimeException {

    public StatementNotFoundException() {
        super();
    }

    public StatementNotFoundException(String message) {
        super(message);
    }

    public StatementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatementNotFoundException(Throwable cause) {
        super(cause);
    }

    protected StatementNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}