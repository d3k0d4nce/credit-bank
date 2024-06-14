package ru.kishko.deal.exceptions.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;

import java.time.LocalDate;

@Component
public class PassportIssueDateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return FinishRegistrationRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FinishRegistrationRequestDto dto = (FinishRegistrationRequestDto) target;
        if (dto.getPassportIssueDate() != null) {
            if (!dto.getPassportIssueDate().isBefore(LocalDate.now())) {
                errors.rejectValue("passportIssueDate", "passportIssueDate.invalid", "Passport issue date must be in the past");
            }
        }
    }
}
