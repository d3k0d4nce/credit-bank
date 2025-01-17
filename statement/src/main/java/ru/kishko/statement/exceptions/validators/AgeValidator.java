package ru.kishko.statement.exceptions.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kishko.openapi.model.LoanStatementRequestDto;
import ru.kishko.openapi.model.ScoringDataDto;
import ru.kishko.statement.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ScoringDataDto.class.isAssignableFrom(clazz) || LoanStatementRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof ScoringDataDto dto) {
            validateBirthdate(dto.getBirthdate(), errors);
        } else if (target instanceof LoanStatementRequestDto dto) {
            validateBirthdate(dto.getBirthdate(), errors);
        }
    }

    private void validateBirthdate(LocalDate birthdate, Errors errors) {
        if (birthdate != null) {
            if (Period.between(birthdate, LocalDate.now()).getYears() < 18) {
                errors.rejectValue("birthdate", "birthdate.invalid", "Birthdate must be at least 18 years ago");
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
