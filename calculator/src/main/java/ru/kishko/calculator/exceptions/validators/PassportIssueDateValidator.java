package ru.kishko.calculator.exceptions.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kishko.calculator.exceptions.ValidationException;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.ScoringDataDto;

import java.time.LocalDate;

@Component
public class PassportIssueDateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ScoringDataDto.class.isAssignableFrom(clazz) || FinishRegistrationRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof ScoringDataDto dto) {
            validatePassportIssueDate(dto.getPassportIssueDate(), errors);
        } else if (target instanceof FinishRegistrationRequestDto dto) {
            validatePassportIssueDate(dto.getPassportIssueDate(), errors);
        }
    }

    private void validatePassportIssueDate(LocalDate passportIssueDate, Errors errors) {
        if (passportIssueDate != null) {
            if (!passportIssueDate.isBefore(LocalDate.now())) {
                errors.rejectValue("passportIssueDate", "passportIssueDate.invalid", "Passport issue date must be in the past");
            }
        }
    }

    public void validate(Object request) {
        BindingResult errors = new BeanPropertyBindingResult(request, "request");
        this.validate(request, errors);
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
