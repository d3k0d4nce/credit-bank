package ru.kishko.api.exceptions;

public class InvalidPassportIssueDate extends RuntimeException {
    public InvalidPassportIssueDate() {
        super();
    }

    public InvalidPassportIssueDate(String message) {
        super(message);
    }

    public InvalidPassportIssueDate(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPassportIssueDate(Throwable cause) {
        super(cause);
    }

    protected InvalidPassportIssueDate(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
