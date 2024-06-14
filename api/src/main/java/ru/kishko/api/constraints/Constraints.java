package ru.kishko.api.constraints;

import ru.kishko.api.exceptions.InvalidAgeException;
import ru.kishko.api.exceptions.InvalidPassportIssueDate;

import java.time.LocalDate;
import java.time.Period;

public class Constraints {
    public static void validateAge(LocalDate birthdate) {
        if (Period.between(birthdate, LocalDate.now()).getYears() < 18) {
            throw new InvalidAgeException("User must be at least 18 years old");
        }
    }

    public static void isValidPassportIssueDate(LocalDate passportIssueDate) {
        if (!passportIssueDate.isBefore(LocalDate.now())) {
            throw new InvalidPassportIssueDate("passportIssueDate must be the past");
        }
    }
}
